package com.cwagritech.arenabookings.service

import com.cwagritech.arenabookings.model.Customer
import com.cwagritech.arenabookings.persistence.CustomerRepository
import org.springframework.stereotype.Service

@Service
class CustomerService(val customerRepository: CustomerRepository) {
    fun findAllCustomers(): MutableIterable<Customer> {
        return customerRepository.findAll()
    }
}