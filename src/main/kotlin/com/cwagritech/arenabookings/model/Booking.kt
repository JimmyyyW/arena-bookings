package com.cwagritech.arenabookings.model

import java.time.OffsetDateTime
import javax.persistence.*

@Entity(name = "bookings")
data class Booking(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val bookingId: Int? = null,
    @ManyToOne
    val horse: Horse,
    val startTime: OffsetDateTime,
    val endTime: OffsetDateTime,
    val jumps: Boolean,
    val sharing: Boolean
)

@Entity(name = "horses")
data class Horse(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val horseId: Int? = null,
    @ManyToOne
    val customer: Customer,
    val name: String
)