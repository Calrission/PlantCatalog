package com.cit.database

import com.cit.database.payment_methods.PaymentMethod
import com.cit.database.payment_methods.PaymentMethods
import com.cit.database.plant.Plant
import com.cit.database.plant.Plants
import com.cit.getLocalProperty
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import java.io.File

class InitializationDatabaseContent {
    companion object{
        private val initJsonPath = getLocalProperty("init_filename")
        private val plants = Json.decodeFromString<List<Plant>>(string = File(initJsonPath).inputStream().readBytes().toString(Charsets.UTF_8))

        private val paymentMethods = listOf(
            PaymentMethod(0, "MasterCard", "mastercard.png"),
            PaymentMethod(1, "PayPal", "paypal.ong"),
            PaymentMethod(2, "Ovo", "ovo.png")
        )

        fun init(){
            paymentMethods.filter { PaymentMethods.select { PaymentMethods.title eq it.title}.empty() }.forEach { model ->
                PaymentMethods.insert {
                    it[title] = model.title
                    it[cover] = model.cover
                }
            }

            plants.filter { Plants.select { Plants.id eq it.id }.empty() }.forEach{ plant ->
                Plants.insert {
                    it[id] = plant.id
                    it[title] = plant.title
                    it[description] = plant.description
                    it[cover] = plant.cover
                    it[price] = plant.price
                    it[room] = plant.room
                }
            }
        }
    }
}