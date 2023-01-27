package com.cit.routings

import com.cit.getLocalProperty
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun Application.configureFileRouting(){
    routing{
        get("img/{img}") {
            val img = call.parameters["img"]
            if (img != null){
                call.respondFile(File("${getLocalProperty("imgs_path")}/$img"))
            }else{
                call.respond("Image not found")
            }
        }
    }
}