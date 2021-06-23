package com.cwagritech.arenabookings.config

import com.cwagritech.arenabookings.persistence.UserRepository
import org.springframework.core.annotation.Order
import org.springframework.http.HttpHeaders
import org.springframework.security.core.context.SecurityContextHolder

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource

import org.springframework.security.core.userdetails.UserDetails

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Component

import java.io.IOException

import javax.servlet.ServletException

import javax.servlet.FilterChain

import javax.servlet.http.HttpServletResponse

import javax.servlet.http.HttpServletRequest

import org.springframework.web.filter.OncePerRequestFilter
import java.util.*


@Component
class JwtTokenFilter(
    private val jwtTokenUtil: JwtTokenUtil,
    private val userRepo: UserRepository
) : OncePerRequestFilter() {

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain
    ) {
        // Get authorization header and validate
        val header = request.getHeader(HttpHeaders.AUTHORIZATION)
        if (header.isEmpty() || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response)
            return
        }

        // Get jwt token and validate
        val token = header.split(" ".toRegex()).toTypedArray()[1].trim { it <= ' ' }
        if (!jwtTokenUtil.validate(token)) {
            chain.doFilter(request, response)
            return
        }

        // Get user identity and set it on the spring security context
        val userDetails: UserDetails? = userRepo.findByUsername(jwtTokenUtil.getUsername(token))
        val authentication = UsernamePasswordAuthenticationToken(
            userDetails, null,
            Optional.ofNullable(userDetails).map { obj: UserDetails -> obj.authorities }.orElse(emptyList())
        )
        authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
        SecurityContextHolder.getContext().authentication = authentication
        chain.doFilter(request, response)
    }
}