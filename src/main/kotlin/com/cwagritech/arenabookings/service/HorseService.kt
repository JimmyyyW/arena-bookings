package com.cwagritech.arenabookings.service

import com.cwagritech.arenabookings.controller.CreateHorseRequest
import com.cwagritech.arenabookings.model.Horse
import com.cwagritech.arenabookings.persistence.HorseRepository
import org.springframework.stereotype.Service

@Service
class HorseService(private val horseRepository: HorseRepository) {

    fun createHorse(createHorseRequest: CreateHorseRequest): Horse {
        return horseRepository.save(Horse(null, createHorseRequest.name))
    }

    fun getAllHorses(): MutableIterable<Horse> {
        return horseRepository.findAll()
    }
}