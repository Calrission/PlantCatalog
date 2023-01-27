package com.cit.database.plant

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class Plant(
    val id: Int,
    val title: String,
    val description: String,
    val cover: String,
    val price: Int,
    val room: String
){
    companion object{
        fun getEmptyInstance(): Plant = Plant(-1, "", "", "", 0, "")
    }
}

object Plants: Table() {
    val id = integer("id").autoIncrement()
    val title = varchar("title", 500)
    val description = varchar("description", 5000)
    val cover = varchar("cover", 150)
    val price = integer("price")
    val room = varchar("room", 150)

    override val primaryKey = PrimaryKey(id)
}