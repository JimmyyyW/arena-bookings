package com.cwagritech.arenabookings.persistence

import com.cwagritech.arenabookings.model.Booking
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.time.OffsetDateTime

@Repository
interface BookingRepository : CrudRepository<Booking, Int> {

    fun findAllByStartTimeIsBetween(startTime: OffsetDateTime, endTime: OffsetDateTime): List<Booking>
}