package com.cit.models

import com.cit.database.plant.Plant
import kotlinx.serialization.Serializable

@Serializable
data class ModelRoom(
    val title: String,
    val plants: List<Plant>
)
