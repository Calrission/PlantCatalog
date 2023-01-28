package com.cit.database.carts

import com.cit.database.plant.Plant
import com.cit.database.user.UserViewModel
import com.cit.models.ModelCartItem
import org.jetbrains.exposed.sql.Table

data class CartItem(
    val id: Int,
    val idUser: Int,
    val idPlant: Int,
    var count: Int
){
    fun toModelCartItem(plant: Plant): ModelCartItem = ModelCartItem(id, plant, count)
}

object Carts: Table() {
    val id = integer("id").autoIncrement()
    val idUser = integer("idUser")
    val idPlant = integer("idPlant")
    val count = integer("count")

    override val primaryKey = PrimaryKey(id)
}