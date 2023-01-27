package com.cit.database.carts

import com.cit.database.plant.Plant
import com.cit.database.user.UserViewModel
import com.cit.models.ModelCart
import org.jetbrains.exposed.sql.Table

data class Cart(
    val id: Int,
    val idUser: Int,
    val idPlant: Int,
    val count: Int
){
    fun toModelCart(user: UserViewModel, plant: Plant): ModelCart = ModelCart(id, plant, user, count)
}

object Carts: Table() {
    val id = integer("id").autoIncrement()
    val idUser = integer("idUser")
    val idPlant = integer("idPlant")
    val count = integer("count")
}