package com.cwagritech.arenabookings.model

import javax.persistence.*

@Entity(name = "customers")
data class Customer(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val customerId: Int? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val phoneNumber: String? = null,
    val addressOne: String? = null,
    val addressTwo: String? = null,
    val city: String? = null,
    val county: String? = null,
    val postCode: String? = null,
    @OneToMany(targetEntity = Horse::class, fetch = FetchType.EAGER, mappedBy = "customer")
    val horses: List<Horse>? = null

)