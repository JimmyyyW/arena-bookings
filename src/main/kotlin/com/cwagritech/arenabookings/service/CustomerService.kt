package com.cwagritech.arenabookings.service

import com.cwagritech.arenabookings.controller.HttpCreateCustomerRequest
import com.cwagritech.arenabookings.controller.HttpUpdateCustomerRequest
import com.cwagritech.arenabookings.model.Customer
import com.cwagritech.arenabookings.persistence.CustomerRepository
import org.springframework.stereotype.Service

@Service
class CustomerService(val customerRepository: CustomerRepository) {
    fun findAllCustomers(): MutableIterable<Customer> {
        return customerRepository.findAll()
    }

    fun createCustomer(customer: HttpCreateCustomerRequest): Customer {
        return customerRepository.save(
            Customer(
                firstName = customer.firstName,
                lastName = customer.lastName,
                email = customer.email,
                phoneNumber = customer.phoneNumber,
                addressOne = customer.addressOne,
                addressTwo = customer.addressTwo,
                city = customer.city,
                county = customer.county,
                postCode = customer.postCode,
                horses = emptyList()
            )
        )
    }

    fun updateCustomer(customerId: Int, customerRequest: HttpUpdateCustomerRequest): Customer {
        val existingCustomer = customerRepository.findById(customerId)
        if (existingCustomer.isPresent) {
            val customer = Customer(
                customerId = customerId,
                firstName = customerRequest.firstName,
                lastName = customerRequest.lastName,
                email = customerRequest.email,
                phoneNumber = customerRequest.phoneNumber,
                addressOne = customerRequest.addressOne,
                addressTwo = customerRequest.addressTwo,
                city = customerRequest.city,
                county = customerRequest.county,
                postCode = customerRequest.postCode,
                horses = existingCustomer.get().horses
            )
            return customerRepository.save(customer)
        }
        else throw Exception("failed to find existing customer")
    }

    fun deleteCustomer(customerId: String) {
        customerRepository.deleteById(customerId.toInt())
    }
}