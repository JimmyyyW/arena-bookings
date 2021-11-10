package com.cwagritech.arenabookings.model

import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "users")
//@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator::class, property="id")
class User : UserDetails, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int? = null

    @CreatedDate
    private val createdAt: LocalDateTime? = null

    @LastModifiedDate
    private var lastModifiedDate: LocalDateTime? = LocalDateTime.now()

    private var enabled: Boolean = true
    private var username: String? = null
    private var password: String? = null

    @ManyToMany(fetch = FetchType.EAGER)
    var roles: MutableList<UserRole>? = mutableListOf()

    var customerId: Int? = null

    constructor() {}
    constructor(username: String?, password: String?) {
        this.username = username
        this.password = password
        enabled = true
    }

    constructor(username: String?, password: String?, enabled: Boolean, customerId: Int) {
        this.username = username
        this.password = password
        this.enabled = enabled
        this.customerId = customerId
    }

    override fun getAuthorities(): Collection<GrantedAuthority?> {
        val grantedAuthorities = mutableListOf<GrantedAuthority?>()
        if (roles != null) {
            for (role in roles!!) {
                grantedAuthorities.add(SimpleGrantedAuthority(role.roleName?.name))
            }
        }
        return grantedAuthorities
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