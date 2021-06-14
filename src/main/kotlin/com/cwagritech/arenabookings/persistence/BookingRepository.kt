package com.cwagritech.arenabookings.persistence

import com.cwagritech.arenabookings.model.Booking
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface BookingRepository : CrudRepository<Booking, Int> {

    fun findAllByStartTimeIsBetween(startTime: LocalDateTime, endTime: LocalDateTime): List<Booking>
}