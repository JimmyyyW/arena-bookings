package com.cwagritech.arenabookings.persistence

import com.cwagritech.arenabookings.model.Role
import com.cwagritech.arenabookings.model.User
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.time.Instant

@SpringBootTest
internal class UserRepositoryTest {

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var roleRepository: RoleRepository

    @Autowired
    lateinit var bCryptPasswordEncoder: BCryptPasswordEncoder

//    @Test
//    fun createAndSaveUser() {
//        val user = User(
//            username = "testUser",
//            password = bCryptPasswordEncoder.encode("admin")
//        )
//
//        user.roles = mutableListOf(
//            roleRepository.findByRoleName(Role.ROLE_USER)
//        )
//        val savedUser = userRepository.save(user)
//
//        assertEquals("testUser", savedUser.username)
//        assertEquals("ROLE_USER", user.roles!![0].roleName?.name)
//
//        //userRepository.deleteById(savedUser.id!!)
//    }

    @Test
    fun pass() {
        println(Instant.now())
        println(bCryptPasswordEncoder.encode("admin"))
    }

}