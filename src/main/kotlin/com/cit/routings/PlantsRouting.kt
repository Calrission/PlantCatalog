package com.cit.routings

import com.cit.controllers.PlantController
import com.cit.models.ModelError
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configurePlantsRouting() {

    val plantController = PlantController()

    routing {
        get("plants") {
            call.respond(plantController.getAllPlants())
        }

        get("all-room-plants"){
            call.respond(plantController.getAllPlantsRooms())
        }

        get("room-plants"){
            val room = call.parameters["room"]
            if (room == null)
                call.respond(ModelError("check 'room' field query parameter"))
            else
                call.respond(plantController.getPlantsRoom(room))
        }

        get("plant") {
            val id = call.parameters["id"]
            if (id == null)
                call.respond(ModelError("check 'id' field query parameter"))
            else
                call.respond(plantController.getPlant(id.toInt()) ?: ModelError("Plant not found"))
        }

        get("popular") {
            call.respond(plantController.getPopular())
        }

        get("search") {
            val q = call.parameters["q"]
            if (q == null){
                call.respond(ModelError("check 'q' field query parameter"))
            }else{
                call.respond(plantController.search(q))
            }
        }
    }
}