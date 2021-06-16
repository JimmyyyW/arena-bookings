package com.cwagritech.arenabookings.model

import java.time.LocalDateTime
import javax.persistence.*

@Entity(name = "bookings")
data class Booking(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val horseId: Int? = null,
    @ManyToOne
    val customer: Customer,
    val name: String
)