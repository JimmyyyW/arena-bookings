package com.cwagritech.arenabookings.controller

import com.cwagritech.arenabookings.model.Booking
import com.cwagritech.arenabookings.persistence.BookingRepository
import com.cwagritech.arenabookings.persistence.HorseRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
class BookingController(
    val bookingRepository: BookingRepository,
    val horseRepository: HorseRepository
) {

    @PostMapping("/bookings")
    fun createBooking(@RequestBody booking: HttpBookingRequest): Booking {
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

    @GetMapping("/bookings")
    fun getBookings(): MutableIterable<Booking> {
        return bookingRepository.findAll()
    }
}

data class HttpBookingRequest(
    val horseId: Int,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val jumps: Boolean,
    val sharing: Boolean
)
