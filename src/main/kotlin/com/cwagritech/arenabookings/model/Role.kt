package com.cwagritech.arenabookings.model

import javax.persistence.*

@Entity(name = "roles")
class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val roleId: Int? = null,
    val name: String,
    @ManyToMany
    val users: List<User>
)