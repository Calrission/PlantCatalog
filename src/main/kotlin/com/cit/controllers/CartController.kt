package com.cit.controllers

import com.cit.database.carts.CartItem
import com.cit.database.carts.Carts
import com.cit.database.carts.DAOCart
import com.cit.database.plant.DAOPlant
import com.cit.database.plant.Plants
import com.cit.database.user.DAOUser
import com.cit.models.ModelCart
import com.cit.models.ModelError
import org.jetbrains.exposed.sql.and

class CartController {
    private val daoCart = DAOCart()
    private val daoUser = DAOUser()
    private val daoPlants = DAOPlant()

    suspend fun getCartUser(token: String): Any{
        val user = daoUser.getUserWithToken(token) ?: return ModelError("User not found")
        return ModelCart(user.convertToViewModel(), daoCart.selectMany {
            Carts.idUser eq user.id
        }.mapNotNull {
            val plant = daoPlants.selectSingle { Plants.id eq it.idPlant }
            if (plant == null)
                null
            else
                it.toModelCartItem(plant)
        })
    }

    suspend fun insertUpdateCartItemUser(idUser: Int, idPlant: Int, count: Int): Any{
        val plant = daoPlants.selectSingle { Plants.id eq idPlant } ?: return ModelError("Plant id=$idUser not found")
        if (daoCart.checkExist(idUser, idPlant)){
            val cartItem = daoCart.selectSingle { (Carts.idUser eq idUser).and(Carts.idPlant eq idPlant) } ?: return ModelError("Exist cart item not update")
            cartItem.count = count
            daoCart.edit(cartItem){
                Carts.id eq cartItem.id
            }
            return cartItem.toModelCartItem(plant)
        }else{
            val cartItem = daoCart.insert(CartItem(-1, idUser, idPlant, count)) ?: return ModelError("Cart item not inserted")
            return cartItem.toModelCartItem(plant)
        }
    }
}