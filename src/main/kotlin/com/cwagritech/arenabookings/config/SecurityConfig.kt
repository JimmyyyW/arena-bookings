package com.cwagritech.arenabookings.config

import com.cwagritech.arenabookings.persistence.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import javax.servlet.http.HttpServletResponse


@EnableWebSecurity
@EnableGlobalMethodSecurity(
    securedEnabled = true,
    jsr250Enabled = true,
    prePostEnabled = true
)
class SecurityConfig(
    private val userRepo: UserRepository,
    private val jwtTokenFilter: JwtTokenFilter
) : WebSecurityConfigurerAdapter() {
    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(UserDetailsService { username: String? ->
            userRepo.findByUsername(
                username!!
            )
        })
    }

    override fun configure(http: HttpSecurity?) {
        http!!.cors().and().csrf().disable()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .exceptionHandling()
            .authenticationEntryPoint { request, response, ex ->
                response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    ex.message
                )
            }
            .and()
            .authorizeRequests()
            .antMatchers(HttpMethod.GET, "/actuator/health").permitAll()
             // public endpoints
            .antMatchers(HttpMethod.GET, "/").permitAll()
            .antMatchers(HttpMethod.POST, "/login").permitAll()
            .antMatchers(HttpMethod.GET, "/bookings").permitAll()
            .antMatchers(HttpMethod.POST, "/bookings").permitAll()
            .antMatchers(HttpMethod.DELETE, "/bookings/{bookingId}").permitAll()
            .antMatchers(HttpMethod.GET, "/customers").permitAll()
            .antMatchers(HttpMethod.POST, "/customers").permitAll()
            .antMatchers(HttpMethod.DELETE, "/customers/{customerId}").permitAll()
            .antMatchers(HttpMethod.GET, "/horses").permitAll()
            .antMatchers(HttpMethod.POST, "/horses").permitAll()
            .antMatchers(HttpMethod.GET, "/users/me/roles").permitAll()
            .antMatchers("/web/**").permitAll()
             // private endpoints
            .anyRequest().authenticated()
            .and()
            .addFilterAfter(jwtTokenFilter, UsernamePasswordAuthenticationFilter::class.java)
    }

    @Bean
    fun bcryptPasswordEncryptor(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    @Throws(Exception::class)
    override fun authenticationManagerBean(): AuthenticationManager? {
        return super.authenticationManagerBean()
    }

}
