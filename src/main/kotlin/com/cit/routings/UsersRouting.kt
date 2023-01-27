package com.cit.routings

import com.cit.common.ApplicationCallUtils.Companion.receiveModel
import com.cit.controllers.UserController
import com.cit.models.SignInBody
import com.cit.models.SignUpBody
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.response.*

fun Application.configureUsersRouting() {

    val userController = UserController()

    routing {
        post("/signIn") {
            val body = call.receiveModel<SignInBody>() ?: return@post
            call.respond(userController.signIn(body))
        }

        post("/signUp"){
            val body = call.receiveModel<SignUpBody>() ?: return@post
            call.respond(userController.signUp(body))
        }
    }
}