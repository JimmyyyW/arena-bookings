package com.cwagritech.arenabookings

import com.cwagritech.arenabookings.persistence.HorseRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.time.LocalDateTime

@SpringBootTest
class ArenaBookingsApplicationTests {

    @Autowired
    lateinit var horseRepository: HorseRepository

    @Autowired
    lateinit var bCryptPasswordEncoder: BCryptPasswordEncoder

    @Test
    fun contextLoads() {
        println(bCryptPasswordEncoder.encode("admin"))
    }

}

class demo {
    @Test
    fun contextLoads() {
        val x = LocalDateTime.now()

        println(x)
    }
}
