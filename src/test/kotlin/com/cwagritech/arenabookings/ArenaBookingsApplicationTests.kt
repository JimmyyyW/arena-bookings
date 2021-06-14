package com.cwagritech.arenabookings

import com.cwagritech.arenabookings.persistence.HorseRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

@SpringBootTest
class ArenaBookingsApplicationTests {

    @Autowired
    lateinit var horseRepository: HorseRepository

    @Test
    fun contextLoads() {
    }

}

class demo {
    @Test
    fun contextLoads() {
        val x = LocalDateTime.now()

        println(x)
    }
}
