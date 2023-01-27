package com.cit.models

import kotlinx.serialization.Serializable

@Serializable
data class SignInBody (
    val login: String,
    val password: String
)