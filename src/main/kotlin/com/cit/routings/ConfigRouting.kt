package com.cit.routings

import com.cit.controllers.PaymentMethodsController
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File
import java.nio.charset.Charset

fun Application.configureConfigRouting(){

    val paymentMethodsController = PaymentMethodsController()

    routing {
        get("config"){
            val fileContent = File("config.json").readText(Charset.forName("utf-8"))
            call.respond(fileContent)
        }

        get("payment_methods"){
            val methods = paymentMethodsController.getMethods()
            call.respond(methods)
        }
    }
}