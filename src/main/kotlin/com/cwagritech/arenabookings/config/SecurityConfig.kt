package com.cwagritech.arenabookings.config

import com.cwagritech.arenabookings.persistence.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.core.annotation.Order
import org.springframework.http.HttpMethod
import org.springframework.security.access.hierarchicalroles.RoleHierarchy
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.lang.Exception
import javax.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler


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
            // Our public endpoints
            .antMatchers(HttpMethod.GET, "/").permitAll()
            .antMatchers(HttpMethod.POST, "/login").permitAll()
            .antMatchers(HttpMethod.GET, "/bookings").permitAll()
            .antMatchers(HttpMethod.POST, "/bookings").permitAll()
            .antMatchers(HttpMethod.GET, "/customers").permitAll()
            .antMatchers(HttpMethod.POST, "/customers").permitAll()
            .antMatchers(HttpMethod.GET, "/horses").permitAll()
            .antMatchers(HttpMethod.POST, "/horses").permitAll()
            .antMatchers("/web/**").permitAll()
            // Our private endpoints
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
