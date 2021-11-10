package com.cwagritech.arenabookings.service

import com.cwagritech.arenabookings.persistence.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserService(val userRepository: UserRepository) : UserDetailsService {
    override fun loadUserByUsername(p0: String): UserDetails {
        val user = userRepository.findByUsername(p0)
        if (user == null) {
            throw UsernameNotFoundException("")
        } else {
            return user
        }
    }

}
