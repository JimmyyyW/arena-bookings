package com.cwagritech.arenabookings.controller

import com.cwagritech.arenabookings.model.Role
import com.cwagritech.arenabookings.model.User
import com.cwagritech.arenabookings.persistence.RoleRepository
import com.cwagritech.arenabookings.persistence.UserRepository
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*

@RestController
class UserController(private val userRepository: UserRepository,
                     private val roleRepository: RoleRepository,
                     private val bCryptPasswordEncoder: BCryptPasswordEncoder
) {

    @PostMapping("/users",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun createUser(@RequestBody createUserRequest: CreateUserRequest): User {
        val user = User(
            createUserRequest.username,
            bCryptPasswordEncoder.encode(createUserRequest.password),
            createUserRequest.enabled,
            createUserRequest.customerId
        )
        val role = inferRoleName(createUserRequest.role)?.let { roleRepository.findByRoleName(it) }
        if (role != null) {
            user.roles?.add(role)
        }
        return userRepository.save(user)
    }

    @DeleteMapping("/users/{userId}",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun deleteUser(@PathVariable userId: String): ResponseEntity<Any> {
        val userIdInt = try {
            userId.toInt()
        } catch (e: Exception) {
            return ResponseEntity.badRequest().body(DeleteHorseResponse("invalid value for user ID"))
        }
        return try {
            userRepository.deleteById(userIdInt)
            ResponseEntity.noContent().build()
        } catch (e: Exception) {
            ResponseEntity.unprocessableEntity().build()
        }
    }

    private fun inferRoleName(role: String): Role? {
        return when (role) {
            "User" -> Role.ROLE_USER
            "Admin" -> Role.ROLE_ADMIN
            else -> null
        }
    }
}

data class CreateUserRequest(
    val username: String,
    val password: String,
    val enabled: Boolean,
    val customerId: Int,
    val role: String
)