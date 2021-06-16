package com.cwagritech.arenabookings.persistence

import com.cwagritech.arenabookings.model.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: CrudRepository<User, Int> {

    fun findByUsername(username: String): User
}