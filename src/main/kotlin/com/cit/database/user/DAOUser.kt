package com.cit.database.user

import com.cit.database.DAOTable
import com.cit.database.DatabaseFactory
import com.cit.models.SignInBody
import com.cit.models.SignUpBody
import org.jetbrains.exposed.sql.*
import java.util.UUID

class DAOUser: DAOTable<User> {

    override suspend fun edit(model: User, where: SqlExpressionBuilder.() -> Op<Boolean>): Boolean {
        return DatabaseFactory.pushQuery {
            Users.update(where) {
                it[token] = model.token
                it[email] = model.email
                it[login] = model.login
                it[password] = model.password
            } > 0
        }
    }

    suspend fun changeToken(token: String, where: SqlExpressionBuilder.() -> Op<Boolean>): Boolean{
        return DatabaseFactory.pushQuery {
            Users.update(where){
                it[Users.token] = token
            } > 0
        }
    }

    override fun resultRowToModel(row: ResultRow): User = User(
        id = row[Users.id],
        token = row[Users.token],
        password = row[Users.password],
        login = row[Users.login],
        email = row[Users.email]
    )

    override suspend fun selectAll(): List<User> {
        return DatabaseFactory.pushQuery {
            Users.selectAll().map(::resultRowToModel)
        }
    }

    override suspend fun selectSingle(where: SqlExpressionBuilder.() -> Op<Boolean>): User? {
        return DatabaseFactory.pushQuery {
            Users.select(where)
                .map(::resultRowToModel)
                .singleOrNull()
        }
    }

    override suspend fun selectMany(where: SqlExpressionBuilder.() -> Op<Boolean>): List<User> {
        return DatabaseFactory.pushQuery {
            Users.select(where)
                .map(::resultRowToModel)
        }
    }

    override suspend fun delete(where: SqlExpressionBuilder.() -> Op<Boolean>): Boolean {
        return DatabaseFactory.pushQuery {
            Users.deleteWhere(op = where) > 0
        }
    }

    override suspend fun checkExist(model: User): Boolean {
        return selectSingle {
            (Users.login eq model.login).and(Users.password eq model.password).and(Users.email eq model.email).and(Users.token eq model.token)
        } != null
    }

    suspend fun checkExist(model: SignInBody): Boolean{
        return selectSingle { (Users.login eq model.login).and(Users.password eq model.password) } != null
    }

    override suspend fun insert(model: User): User? {
        return DatabaseFactory.pushQuery {
            Users.insert {
                it[token] = model.token
                it[login] = model.login
                it[email] = model.email
                it[password] = model.password
            }.resultedValues?.singleOrNull()?.let(::resultRowToModel)
        }
    }

    suspend fun signUp(model: SignUpBody): User?{
        insert(User(-1, model.login, model.email, model.password, "")) ?: return null
        generateNewTokenUser(model.login) ?: return null
        return selectSingle { (Users.login eq model.login) and (Users.password eq model.password) and (Users.email eq model.email)}
    }

    suspend fun checkExistUserEmail(email: String): Boolean{
        return selectSingle { Users.email eq email } != null
    }

    suspend fun checkExistUserToken(token: String): Boolean{
        return selectSingle { Users.token eq token } != null
    }

    suspend fun checkExistUserLogin(login: String): Boolean{
        return selectSingle { Users.login eq login } != null
    }

    suspend fun getUserWithToken(token: String): User?{
        return selectSingle { Users.token eq token }
    }
    
    suspend fun checkEqualPasswordUser(login: String, password: String): Boolean{
        val user = selectSingle { Users.login eq login } ?: return false
        return user.password == password
    }
    
    suspend fun generateNewTokenUser(login: String): String?{
        val token = UUID.randomUUID().toString()
        return if (changeToken(token) { Users.login eq login }){
            token
        }else{
            null
        }
    }
}