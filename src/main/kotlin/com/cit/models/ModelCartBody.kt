package com.cit.models

import com.cit.database.carts.CartItem
import kotlinx.serialization.Serializable

@Serializable
data class ModelCartBody (
    val idPlant: Int,
    val count: Int
){
    fun toCart(idUser: Int): CartItem = CartItem(-1, idUser, idPlant, count)
}