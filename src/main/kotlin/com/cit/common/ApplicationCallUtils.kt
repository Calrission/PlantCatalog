package com.cit.common

import com.cit.models.ModelError
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import java.lang.Exception

class ApplicationCallUtils {
    companion object {
        suspend inline fun <reified T : Any> ApplicationCall.receiveModel(): T?{
            val fields = T::class.java.declaredFields.map { it.name }.filter { it != "Companion" }
            return try {
                receive()
            } catch (e: Exception) {
                respond(ModelError("Error body, please check exist fields: ${fields.joinToString { it }}"))
                null
            }
        }
    }
}