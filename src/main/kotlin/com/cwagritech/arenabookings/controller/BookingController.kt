package com.cwagritech.arenabookings.controller

import com.cwagritech.arenabookings.model.Booking
import com.cwagritech.arenabookings.persistence.BookingRepository
import com.cwagritech.arenabookings.persistence.HorseRepository
import com.cwagritech.arenabookings.service.BookingService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
class BookingController(
    val bookingService: BookingService
) {

    @PostMapping("/bookings")
    fun createBooking(@RequestBody booking: HttpBookingRequest): Booking {
        // check dates
        return bookingService.createBooking(booking)
    }

    @GetMapping("/bookings")
    fun getBookings(): MutableIterable<Booking> {
        // add filters
        return bookingService.findAllBookings()
    }
}

data class HttpBookingRequest(
    val horseId: Int,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val jumps: Boolean,
    val sharing: Boolean
)
