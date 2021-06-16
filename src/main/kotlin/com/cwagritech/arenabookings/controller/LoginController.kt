package com.cwagritech.arenabookings.controller

import com.cwagritech.arenabookings.config.JwtTokenUtil
import com.cwagritech.arenabookings.model.LoginRequest
import com.cwagritech.arenabookings.model.LoginResponse
import com.cwagritech.arenabookings.model.User
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.RestController
import org.springframework.http.HttpStatus

import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager

import org.springframework.security.authentication.BadCredentialsException

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication

import org.springframework.web.bind.annotation.RequestBody

import org.springframework.web.bind.annotation.PostMapping
import javax.validation.Valid


@RestController
class LoginController(
    val authenticationManager: AuthenticationManager,
    val jwtTokenUtil: JwtTokenUtil
) {

    @PostMapping("/login")
    fun login(@RequestBody @Valid request: LoginRequest): ResponseEntity<User>? {
        return try {
            val authenticate: Authentication = authenticationManager
                .authenticate(
                    UsernamePasswordAuthenticationToken(
                        request.username, request.password
                    )
                )
            val user: User = authenticate.principal as User
            ResponseEntity.ok()
                .header(
                    HttpHeaders.AUTHORIZATION,
                    jwtTokenUtil.generateAccessToken(user)
                )
                .body(user)
        } catch (ex: BadCredentialsException) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }
    }
}