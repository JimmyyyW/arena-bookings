package com.cwagritech.arenabookings.service

import com.cwagritech.arenabookings.controller.HttpBookingRequest
import com.cwagritech.arenabookings.model.Booking
import com.cwagritech.arenabookings.persistence.BookingRepository
import com.cwagritech.arenabookings.persistence.HorseRepository
import org.springframework.stereotype.Service

@Service
class BookingService(
    val bookingRepository: BookingRepository,
    val horseRepository: HorseRepository
) {

    fun createBooking(booking: HttpBookingRequest): Booking {
        val horse = horseRepository.findById(booking.horseId)
        if (horse.isEmpty) {
            throw Exception("couldnt find horse by id ${booking.horseId}")
        }
        val realBooking = Booking(
            null,
            horse = horse.get(),
            startTime = booking.startTime,
            endTime = booking.endTime,
            sharing = booking.sharing,
            jumps = booking.jumps
        )
        return bookingRepository.save(realBooking)
    }

    fun findAllBookings(): MutableIterable<Booking> {
        return bookingRepository.findAll()
    }
}