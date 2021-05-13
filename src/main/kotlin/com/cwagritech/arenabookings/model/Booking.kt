package com.cwagritech.arenabookings.model

import java.time.LocalDateTime
import javax.persistence.*

@Entity(name = "bookings")
data class Booking(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val bookingId: Int? = null,
    @ManyToOne
    val horse: Horse,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val jumps: Boolean,
    val sharing: Boolean
)

@Entity(name = "horses")
data class Horse(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val horseId: Int? = null,
    val name: String
)