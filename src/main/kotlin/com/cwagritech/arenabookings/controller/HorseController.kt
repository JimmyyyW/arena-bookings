package com.cwagritech.arenabookings.controller

import com.cwagritech.arenabookings.common.Either
import com.cwagritech.arenabookings.common.ErrorMessage
import com.cwagritech.arenabookings.config.JwtTokenUtil
import com.cwagritech.arenabookings.model.Horse
import com.cwagritech.arenabookings.model.UserRole
import com.cwagritech.arenabookings.service.HorseService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.annotation.security.RolesAllowed

@RestController
class HorseController(val horseService: HorseService,
                      val jwtTokenUtil: JwtTokenUtil) {

    @RolesAllowed(UserRole.ADMIN)
    @PostMapping("/horses",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun createHorse(@RequestBody horse: CreateHorseRequest): ResponseEntity<Any> {
        return when (val horseOrError = horseService.createHorse(horse)) {
            is Either.Left -> ResponseEntity<Any>(horseOrError.value, HttpStatus.CREATED)
            is Either.Right -> ResponseEntity<Any>(ErrorMessage(horseOrError.value.message), HttpStatus.NOT_FOUND)
        }
    }

    @RolesAllowed(UserRole.ADMIN, UserRole.USER)
    @GetMapping("/horses",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun getHorses(): MutableIterable<Horse> {
        return horseService.getAllHorses()
    }

    @RolesAllowed(UserRole.ADMIN, UserRole.USER)
    @GetMapping("/me/horses",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun getMyHorses(requestEntity: RequestEntity<Any>): MutableList<Horse>? {
        val jwt = requestEntity.headers["Authorization"]?.first()?.substringAfterLast(" ")
        val userId = jwtTokenUtil.getUserId(jwt)

        return horseService.getAllHorsesByUserId(userId = userId)

    }

    @DeleteMapping("/horses/{horseId}",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @RolesAllowed(UserRole.ADMIN)
    fun deleteHorse(@PathVariable horseId: String): ResponseEntity<DeleteHorseResponse> {
        val horseIdInt = try {
            horseId.toInt()
        } catch (e: Exception) {
            return ResponseEntity.badRequest().body(DeleteHorseResponse("invalid value for horse ID"))
        }
        return try {
            horseService.deleteHorse(horseId = horseIdInt)
            ResponseEntity.noContent().build()
        } catch (e: Exception) {
            ResponseEntity.unprocessableEntity().build()
        }
    }

}

data class CreateHorseRequest(
    val name: String,
    val customerId: Int
)

data class DeleteHorseResponse(
    val message: String
)