package com.cit.database.carts

import com.cit.database.DAOTable
import com.cit.database.DatabaseFactory
import org.jetbrains.exposed.sql.*

class DAOCart: DAOTable<Cart> {
    override fun resultRowToModel(row: ResultRow): Cart {
        return Cart(
            id  = row[Carts.id],
            idUser = row[Carts.idUser],
            idPlant = row[Carts.idPlant],
            count = row[Carts.count]
        )
    }

    override suspend fun selectAll(): List<Cart> {
        return DatabaseFactory.pushQuery {
            Carts.selectAll()
                .map(::resultRowToModel)
        }
    }

    override suspend fun selectSingle(where: SqlExpressionBuilder.() -> Op<Boolean>): Cart? {
        return DatabaseFactory.pushQuery {
            Carts.select(where)
                .map(::resultRowToModel)
                .singleOrNull()
        }
    }

    override suspend fun selectMany(where: SqlExpressionBuilder.() -> Op<Boolean>): List<Cart> {
        return DatabaseFactory.pushQuery {
            Carts.select(where)
                .map(::resultRowToModel)
        }
    }

    override suspend fun delete(where: SqlExpressionBuilder.() -> Op<Boolean>): Boolean {
        return DatabaseFactory.pushQuery { Carts.deleteWhere(op = where) > 0 }
    }

    override suspend fun checkExist(model: Cart): Boolean {
        return selectSingle {
            (Carts.id eq model.id)
                .and(Carts.idUser eq model.idUser)
                .and(Carts.idPlant eq model.idPlant)
        } != null
    }

    override suspend fun insert(model: Cart): Cart? {
        return DatabaseFactory.pushQuery {
            Carts.insert {
                it[idUser] = model.idUser
                it[idPlant] = model.idPlant
                it[count] = model.count
            }.resultedValues?.singleOrNull()?.let(::resultRowToModel)
        }
    }

    override suspend fun edit(model: Cart, where: SqlExpressionBuilder.() -> Op<Boolean>): Boolean {
        return DatabaseFactory.pushQuery {
            Carts.update {
                it[idUser] = model.idUser
                it[idPlant] = model.idPlant
            }
        } > 0
    }
}