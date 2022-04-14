package com.cwagritech.arenabookings.controller

import com.cwagritech.arenabookings.common.Either
import com.cwagritech.arenabookings.common.ErrorMessage
import com.cwagritech.arenabookings.model.Booking
import com.cwagritech.arenabookings.service.BookingService
import com.cwagritech.arenabookings.service.HorseService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.OffsetDateTime

@RestController
class BookingController(
    val bookingService: BookingService,
    val horseService: HorseService
) {

    @PostMapping("/bookings")
    fun createBooking(@RequestBody httpBookingRequest: HttpBookingRequest): ResponseEntity<Any> {

        val booking = Booking(
            bookingId = null,
            horse = horseService.findHorseById(httpBookingRequest.horseId),
            startTime = httpBookingRequest.startTime,
            endTime = httpBookingRequest.endTime,
            jumps = httpBookingRequest.jumps,
            sharing = httpBookingRequest.sharing
        )

        return when(val bookingOrError = bookingService.createBooking(booking)) {
            is Either.Left -> ResponseEntity<Any>(bookingOrError.value, HttpStatus.CREATED)
            is Either.Right -> ResponseEntity<Any>(ErrorMessage(bookingOrError.value.message), HttpStatus.NOT_ACCEPTABLE)
        }
    }

    @GetMapping("/bookings")
    fun getBookings(): List<HttpBookingResponse> {
        // add filters
        return bookingService.findAllBookings()
            .map { HttpBookingResponse(
                it,
                it.horse.customer.customerId
            ) }
    }

    @DeleteMapping("/bookings/{bookingId}")
    fun deleteBooking(@PathVariable bookingId: Int) {
        bookingService.deleteBooking(bookingId)
    }
}

data class HttpBookingResponse(
    val booking: Booking,
    val customerId: Int?
)

data class HttpBookingRequest(
    val horseId: Int,
    val startTime: OffsetDateTime,
    val endTime: OffsetDateTime,
    val jumps: Boolean,
    val sharing: Boolean
)
