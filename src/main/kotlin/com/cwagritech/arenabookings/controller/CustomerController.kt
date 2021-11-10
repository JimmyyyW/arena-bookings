package com.cwagritech.arenabookings.controller

import com.cwagritech.arenabookings.model.Customer
import com.cwagritech.arenabookings.model.Horse
import com.cwagritech.arenabookings.model.UserRole
import com.cwagritech.arenabookings.service.CustomerService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.lang.Nullable
import org.springframework.web.bind.annotation.*

@RestController
class CustomerController(val customerService: CustomerService) {

    @GetMapping("/customers", produces = [MediaType.APPLICATION_JSON_VALUE])
    //@PreAuthorize("hasAuthority('ADMIN')")
    //@RolesAllowed(UserRole.ADMIN)
    fun getCustomers(): MutableIterable<Customer> {
        return customerService.findAllCustomers().toSortedSet { customer1, customer2 ->
            customer1.firstName!![0].compareTo(customer2.firstName!![0])
        }
            .onEach {
                it.users?.forEach { user ->
                    val roleList = mutableListOf<UserRole>()
                    user.roles?.forEach { role ->
                        roleList.add(UserRole(id = role.id, roleName = role.roleName))
                    }
                    user.roles = roleList
                }
            }
    }

    @PostMapping("/customers",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun createCustomer(@RequestBody customer: HttpCreateCustomerRequest): Customer {
        return customerService.createCustomer(customer)
    }

    @PutMapping("/customers/{customerId}",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun updateCustomer(@RequestBody updateCustomerRequest: HttpUpdateCustomerRequest,
                       @PathVariable customerId: String): ResponseEntity<Customer> {
        val customerIdInteger = try {
            customerId.toInt()
        } catch (e: Exception) {
            return ResponseEntity.badRequest().build()
        }
        return try {
            ResponseEntity.ok(customerService.updateCustomer(customerIdInteger, updateCustomerRequest))
        } catch (e: Exception) {
            ResponseEntity.notFound().build()
        }
    }

}

data class HttpUpdateCustomerRequest(
    val email: String,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val addressOne: String,
    val addressTwo: String?,
    val city: String,
    val county: String,
    val postCode: String,
)


data class HttpCreateCustomerRequest (
    val email: String,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val addressOne: String,
    val addressTwo: String?,
    val city: String,
    val county: String,
    val postCode: String,
    @get:Nullable
    val horses: List<Horse>?
)