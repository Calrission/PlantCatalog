package com.cit.database.payment_methods

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class PaymentMethod(
    val id: Int,
    val title: String,
    val cover: String
)

object PaymentMethods: Table() {
    val id = integer("id").autoIncrement()
    val title = varchar("title", 150)
    val cover = varchar("cover", 500)

    override val primaryKey = PrimaryKey(id)
}