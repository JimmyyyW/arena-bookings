//package com.cwagritech.arenabookings.model
//
//import org.springframework.data.annotation.CreatedDate
//import org.springframework.data.annotation.LastModifiedDate
//import org.springframework.security.core.GrantedAuthority
//import org.springframework.security.core.userdetails.UserDetails
//import org.springframework.stereotype.Indexed
//import java.time.LocalDateTime
//import javax.persistence.*
//
//@Entity(name = "users")
//class User(
//    private val username: String,
//    private val password: String): UserDetails {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE)
//    val id: Int? = null
//
//    @CreatedDate
//    val createdAt: LocalDateTime? = null
//
//    @LastModifiedDate
//    val lastModifiedDate: LocalDateTime? = null
//
//    override fun getPassword(): String {
//        return password
//    }
//
//    override fun getUsername(): String {
//        return username
//    }
//
//    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
//        return this.authorities
//    }
//
//    override fun isAccountNonExpired(): Boolean {
//        return isAccountNonExpired
//    }
//
//    override fun isAccountNonLocked(): Boolean {
//        return this.isAccountNonLocked
//    }
//
//    override fun isCredentialsNonExpired(): Boolean {
//        return isCredentialsNonExpired
//    }
//
//    override fun isEnabled(): Boolean {
//        return isEnabled
//    }
//}