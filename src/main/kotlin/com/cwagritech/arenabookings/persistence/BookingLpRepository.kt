package com.cwagritech.arenabookings.persistence

import com.cwagritech.arenabookings.model.BookingLp
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.time.OffsetDateTime

@Repository
interface BookingLpRepository : CrudRepository<BookingLp, Int> {
    //aa
    fun findAllByStartTimeIsBetween(startTime: OffsetDateTime, endTime: OffsetDateTime): List<BookingLp>
}