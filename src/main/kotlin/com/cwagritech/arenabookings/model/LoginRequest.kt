package com.cwagritech.arenabookings.model

import javax.validation.constraints.NotNull

class LoginRequest(
    @get:NotNull
    //@get:Email
    val username: String,
    @get:NotNull
    val password: String
) {
}