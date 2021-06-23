package com.cwagritech.arenabookings.controller

import com.cwagritech.arenabookings.model.Customer
import com.cwagritech.arenabookings.model.UserRole
import com.cwagritech.arenabookings.service.CustomerService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import javax.annotation.security.RolesAllowed

@RestController
class CustomerController(val customerService: CustomerService) {

    @GetMapping("/customers", produces = [MediaType.APPLICATION_JSON_VALUE])
    //@PreAuthorize("hasAuthority('ADMIN')")
    @RolesAllowed(UserRole.USER)
    fun getCustomers(): MutableIterable<Customer> {
        return customerService.findAllCustomers()
    }
}