package com.cwagritech.arenabookings.service

import com.cwagritech.arenabookings.common.Either
import com.cwagritech.arenabookings.controller.CreateHorseRequest
import com.cwagritech.arenabookings.model.Horse
import com.cwagritech.arenabookings.persistence.CustomerRepository
import com.cwagritech.arenabookings.persistence.HorseRepository
import com.cwagritech.arenabookings.persistence.UserRepository
import org.springframework.stereotype.Service

@Service
class HorseService(
    private val horseRepository: HorseRepository,
    private val customerRepository: CustomerRepository,
    private val userRepository: UserRepository
) {

    fun createHorse(createHorseRequest: CreateHorseRequest): Either<Horse, Throwable> {
        val customer = customerRepository.findById(createHorseRequest.customerId)
//        return if (customer.isEmpty()) {
//            Either.Right(Exception("Could not find customer"))
        return Either.Left(horseRepository.save(Horse(null, customer.get(),  createHorseRequest.name)))
    }

    fun getAllHorses(): MutableIterable<Horse> {
        return horseRepository.findAll()
    }

    fun getAllHorsesByUserId(userId: String): MutableList<Horse>? {
        val user = userRepository.findById(userId.toInt())
        return user.get().customerId?.let { horseRepository.findAllByCustomerCustomerId(it) }
    }

    fun findHorseById(horseId: Int): Horse {
        return horseRepository.findById(horseId).orElseThrow { Exception("") }
    }

    fun deleteHorse(horseId: Int) {
        return horseRepository.deleteById(horseId)
    }
}