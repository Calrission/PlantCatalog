package com.cit.routings

import com.cit.controllers.CartController
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureCartRouting(){

    val cartController = CartController()

    routing {
        get("cart"){
            val token = call.parameters["token"]
            if (token == null){
                call.respond("Cart not found")
            }else{
                call.respond(cartController.getCartItemsUser(token))
            }
        }
    }
}