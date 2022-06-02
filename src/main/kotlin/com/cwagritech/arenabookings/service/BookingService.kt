package com.cwagritech.arenabookings.service

import com.cwagritech.arenabookings.common.Either
import com.cwagritech.arenabookings.controller.HttpBookingLpResponse
import com.cwagritech.arenabookings.model.Booking
import com.cwagritech.arenabookings.model.BookingLp
import com.cwagritech.arenabookings.persistence.BookingLpRepository
import com.cwagritech.arenabookings.persistence.BookingRepository
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service

@Service
class BookingService(
    val bookingRepository: BookingRepository,
    val bookingLpRepository: BookingLpRepository,
) {

    fun createBooking(booking: Booking): Either<Booking, Throwable> {
        return if (isBookableSlot(booking)) Either.Left(bookingRepository.save(booking))
        else Either.Right(Exception("slot either taken or not a valid 15 minute period"))
    }

    fun findAllBookings(): MutableIterable<Booking> {
        return bookingRepository.findAll()
    }

    private fun isBookableSlot(booking: Booking): Boolean {
        //check time is multiple of 15
        if (booking.startTime.minute % 15 == 0 &&
            booking.endTime.minute % 15 == 0 &&
            booking.startTime != booking.endTime &&
            booking.startTime.isBefore(booking.endTime)) {

            val currentBookings = bookingRepository.findAllByStartTimeIsBetween(booking.startTime.plusSeconds(1), booking.endTime.minusSeconds(1))
            if (currentBookings.isEmpty()) return true
        }
        return false
    }

    private fun isBookableSlot(booking: BookingLp): Boolean {
        //check time is multiple of 15
        if (booking.startTime.minute % 15 == 0 &&
            booking.endTime.minute % 15 == 0 &&
            booking.startTime != booking.endTime &&
            booking.startTime.isBefore(booking.endTime)) {

            val currentBookings = bookingLpRepository.findAllByStartTimeIsBetween(booking.startTime.plusSeconds(1), booking.endTime.minusSeconds(1))
            if (currentBookings.isEmpty()) return true
        }
        return false
    }


    fun deleteBooking(bookingId: Int) {
        try {
            bookingRepository.deleteById(bookingId)
        } catch (e: EmptyResultDataAccessException) {
            bookingLpRepository.deleteById(bookingId)
        }
    }

    fun findAllBookingsLp(): MutableIterable<BookingLp> {
        return bookingLpRepository.findAll()
    }

    fun createBookingLp(booking: BookingLp):Either<BookingLp, Throwable> {
        return if (isBookableSlot(booking)) Either.Left(bookingLpRepository.save(booking))
        else Either.Right(Exception("slot either taken or not a valid 15 minute period"))
    }

}