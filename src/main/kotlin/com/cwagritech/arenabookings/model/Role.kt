package com.cwagritech.arenabookings.model

import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "roles")
class UserRole(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    val id: Long = 0,
    @Column(name = "role_name")
    @NotNull
    @Enumerated(EnumType.STRING)
    val roleName: Role? = null
) {
    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    val users: List<User>? = null

    companion object {
        const val USER = "ROLE_USER"
        const val ADMIN = "ROLE_ADMIN"
    }
}

enum class Role {
    ADMIN, ROLE_USER
}
