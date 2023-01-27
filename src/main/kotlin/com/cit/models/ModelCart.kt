package com.cit.models

import com.cit.database.plant.Plant
import com.cit.database.user.UserViewModel
import kotlinx.serialization.Serializable

@Serializable
data class ModelCart(
    val id: Int,
    val plant: Plant,
    val user: UserViewModel,
    val count: Int
)
