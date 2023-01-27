package com.cit.database.user

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.isNotNull
import org.jetbrains.exposed.sql.Table

class User(
    val id: Int,
    val login: String,
    val email: String,
    val password: String,
    val token: String
){
    fun convertToViewModel(): UserViewModel {
        return UserViewModel(id, login)
    }
}

@Serializable
data class UserViewModel(
    val id: Int,
    val login: String
)

object Users: Table(){
    val id = integer("id").autoIncrement()
    val login = varchar("login", 100)
    val email = varchar("email", 100)
    val password = varchar("password", 50)
    val token = varchar("token", 500)

    override val primaryKey = PrimaryKey(id)
}