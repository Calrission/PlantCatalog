package com.cit.models

import com.cit.database.plant.Plant
import com.cit.database.user.User
import com.cit.database.user.UserViewModel
import kotlinx.serialization.Serializable

@Serializable
data class ModelCartItem(
    val id: Int,
    val plant: Plant,
    val count: Int
)

@Serializable
data class ModelCart(
    val user: UserViewModel,
    val items: List<ModelCartItem>
)
