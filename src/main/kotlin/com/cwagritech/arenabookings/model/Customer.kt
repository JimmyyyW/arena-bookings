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
    @OneToMany
    val horses: List<Horse>? = null

)