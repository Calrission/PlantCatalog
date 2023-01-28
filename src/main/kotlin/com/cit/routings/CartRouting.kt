package com.cit.routings

import com.cit.common.ApplicationCallUtils.Companion.receiveModel
import com.cit.controllers.CartController
import com.cit.database.user.DAOUser
import com.cit.models.ModelCartBody
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureCartRouting(){

    val cartController = CartController()
    val daoUser = DAOUser()

    routing {
        get("cart"){
            val token = call.parameters["token"]
            if (token == null){
                call.respond("Cart not found")
            }else{
                call.respond(cartController.getCartUser(token))
            }
        }

        post("cart"){
            val token = call.parameters["token"]
            if (token == null){
                call.respond("Cart not found")
            }else{
                val body = call.receiveModel<ModelCartBody>() ?: return@post
                val user = daoUser.getUserWithToken(token) ?: return@post
                call.respond(cartController.insertUpdateCartItemUser(user.id, body.idPlant, body.count))
            }
        }


        delete("cart"){
            val token = call.parameters["token"]
            val idCart = call.parameters["idCart"]
            if (token == null || idCart == null ){
                call.respond("Cart not found")
            }else {
                call.respond(cartController.removeCartItem(idCart.toInt()))
            }
        }
    }
}