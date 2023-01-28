package com.cit.database.carts

import com.cit.database.DAOTable
import com.cit.database.DatabaseFactory
import org.jetbrains.exposed.sql.*

class DAOCart: DAOTable<CartItem> {
    override fun resultRowToModel(row: ResultRow): CartItem {
        return CartItem(
            id  = row[Carts.id],
            idUser = row[Carts.idUser],
            idPlant = row[Carts.idPlant],
            count = row[Carts.count]
        )
    }

    override suspend fun selectAll(): List<CartItem> {
        return DatabaseFactory.pushQuery {
            Carts.selectAll()
                .map(::resultRowToModel)
        }
    }

    override suspend fun selectSingle(where: SqlExpressionBuilder.() -> Op<Boolean>): CartItem? {
        return DatabaseFactory.pushQuery {
            Carts.select(where)
                .map(::resultRowToModel)
                .singleOrNull()
        }
    }

    override suspend fun selectMany(where: SqlExpressionBuilder.() -> Op<Boolean>): List<CartItem> {
        return DatabaseFactory.pushQuery {
            Carts.select(where)
                .map(::resultRowToModel)
        }
    }

    override suspend fun delete(where: SqlExpressionBuilder.() -> Op<Boolean>): Boolean {
        return DatabaseFactory.pushQuery { Carts.deleteWhere(op = where) > 0 }
    }

    override suspend fun checkExist(model: CartItem): Boolean {
        return selectSingle {
            (Carts.id eq model.id)
                .and(Carts.idUser eq model.idUser)
                .and(Carts.idPlant eq model.idPlant)
                .and(Carts.count eq model.count)
        } != null
    }

    suspend fun checkExist(idUser: Int, idPlant: Int): Boolean{
        return selectSingle {
            (Carts.idUser eq idUser).and(Carts.idPlant eq idPlant)
        } != null
    }

    override suspend fun insert(model: CartItem): CartItem? {
        return DatabaseFactory.pushQuery {
            Carts.insert {
                it[idUser] = model.idUser
                it[idPlant] = model.idPlant
                it[count] = model.count
            }.resultedValues?.singleOrNull()?.let(::resultRowToModel)
        }
    }

    override suspend fun edit(model: CartItem, where: SqlExpressionBuilder.() -> Op<Boolean>): Boolean {
        return DatabaseFactory.pushQuery {
            Carts.update(where) {
                it[idUser] = model.idUser
                it[idPlant] = model.idPlant
                it[count] = model.count
            }
        } > 0
    }
}