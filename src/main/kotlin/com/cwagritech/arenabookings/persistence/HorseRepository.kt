package com.cwagritech.arenabookings.persistence

import com.cwagritech.arenabookings.model.Horse
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface HorseRepository : CrudRepository<Horse, Int>{
}