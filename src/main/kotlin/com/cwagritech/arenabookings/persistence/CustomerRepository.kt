package com.cwagritech.arenabookings.persistence

import com.cwagritech.arenabookings.model.Customer
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository : CrudRepository<Customer, Int> {


}