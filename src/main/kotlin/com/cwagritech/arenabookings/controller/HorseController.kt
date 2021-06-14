package com.cwagritech.arenabookings.controller

import com.cwagritech.arenabookings.model.Horse
import com.cwagritech.arenabookings.service.HorseService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class HorseController(val horseService: HorseService) {

    @PostMapping("/horses",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun createHorse(@RequestBody horse: CreateHorseRequest): Horse {
        return horseService.createHorse(horse)
    }

    @GetMapping("/horses",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun getHorses(): MutableIterable<Horse> {
        return horseService.getAllHorses()
    }
}

data class CreateHorseRequest(
    val name: String
)