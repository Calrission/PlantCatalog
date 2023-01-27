package com.cit.controllers

import com.cit.database.plant.DAOPlant
import com.cit.database.plant.Plant
import com.cit.database.plant.Plants
import com.cit.models.ModelRoom

class PlantController {
    private val daoPlant = DAOPlant()

    suspend fun getAllPlants(): List<Plant> = daoPlant.selectAll()

    suspend fun getPlantsRoom(room: String): List<Plant> = daoPlant.selectMany { Plants.room eq room }

    suspend fun getAllPlantsRooms(): List<ModelRoom> {
        val plants = getAllPlants()
        val rooms = plants.map { it.room }
        return rooms.map { room ->
            ModelRoom(room, plants.filter { it.room == room })
        }
    }

    suspend fun getPlant(id: Int): Plant? = daoPlant.selectSingle { Plants.id eq id }

    suspend fun getPopular(): List<Plant> = daoPlant.selectMany { Plants.id.mod(5).eq(0) }

    suspend fun search(q: String): List<Plant> = daoPlant.selectMany { Plants.title.like("%$q%") }
}