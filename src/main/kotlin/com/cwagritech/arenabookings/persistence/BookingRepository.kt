package com.cwagritech.arenabookings.persistence

import com.cwagritech.arenabookings.model.Booking
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface BookingRepository : CrudRepository<Booking, Int> {
}