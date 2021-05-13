package com.cwagritech.arenabookings.controller

import com.cwagritech.arenabookings.model.Horse
import com.cwagritech.arenabookings.persistence.HorseRepository
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class HorseController(val horseRepository: HorseRepository) {

    @PostMapping("/horses",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun createHorse(@RequestBody horse: Horse): Horse {
        return horseRepository.save(horse)
    }

    @GetMapping("/horses",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun getHorses(): MutableIterable<Horse> {
        return horseRepository.findAll()
    }
}