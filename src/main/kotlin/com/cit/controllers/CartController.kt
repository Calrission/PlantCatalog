package com.cit.controllers

import com.cit.database.carts.Carts
import com.cit.database.carts.DAOCart
import com.cit.database.plant.DAOPlant
import com.cit.database.plant.Plant
import com.cit.database.plant.Plants
import com.cit.database.user.DAOUser
import com.cit.models.ModelCart

class CartController {
    private val daoCart = DAOCart()
    private val daoUser = DAOUser()
    private val daoPlants = DAOPlant()

    suspend fun getCartItemsUser(token: String): List<ModelCart>{
        val user = daoUser.getUserWithToken(token) ?: return emptyList()
        return daoCart.selectMany {
            Carts.idUser eq user.id
        }.map { it.toModelCart(user.convertToViewModel(), daoPlants.selectSingle { Plants.id eq it.idPlant } ?: Plant.getEmptyInstance()) }
    }
}