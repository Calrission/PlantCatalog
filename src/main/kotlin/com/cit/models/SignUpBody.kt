package com.cit.models

import kotlinx.serialization.Serializable

@Serializable
data class SignUpBody (
    val email: String,
    val login: String,
    val password: String,
){
    fun toSignInBody(): SignInBody = SignInBody(login, password)
}
