package com.cwagritech.arenabookings.model

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime
import org.springframework.data.annotation.LastModifiedDate
import javax.persistence.ManyToMany
import org.springframework.security.core.GrantedAuthority
import java.io.Serializable
import java.util.ArrayList
import javax.persistence.Entity
import javax.persistence.Id

@Entity(name = "users")
class User : UserDetails, Serializable {
    @Id
    val id = 0

    @CreatedDate
    private val createdAt: LocalDateTime? = null

    @LastModifiedDate
    private val lastModifiedDate: LocalDateTime? = null
    private var enabled = true
    private var username: String? = null
    private var password: String? = null

    @ManyToMany
    private val authorities: List<Role> = ArrayList()

    constructor() {}
    constructor(username: String?, password: String?) {
        this.username = username
        this.password = password
        enabled = true
    }

    override fun getAuthorities(): Collection<GrantedAuthority?> {
        return emptyList()
    }

    override fun getPassword(): String {
        return password!!
    }

    override fun getUsername(): String {
        return username!!
    }

    override fun isAccountNonExpired(): Boolean {
        return enabled
    }

    override fun isAccountNonLocked(): Boolean {
        return enabled
    }

    override fun isCredentialsNonExpired(): Boolean {
        return enabled
    }

    override fun isEnabled(): Boolean {
        return enabled
    }
}