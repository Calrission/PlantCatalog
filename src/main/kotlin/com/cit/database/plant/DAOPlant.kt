package com.cit.database.plant

import com.cit.database.DAOTable
import com.cit.database.DatabaseFactory.pushQuery
import com.cit.database.plant.Plants.cover
import com.cit.database.plant.Plants.description
import com.cit.database.plant.Plants.id
import com.cit.database.plant.Plants.price
import com.cit.database.plant.Plants.room
import com.cit.database.plant.Plants.title
import org.jetbrains.exposed.sql.*

class DAOPlant: DAOTable<Plant> {
    override fun resultRowToModel(row: ResultRow): Plant {
        return Plant(
            id  = row[id],
            title = row[title],
            description = row[description],
            cover = row[cover],
            price = row[price],
            room = row[room]
        )
    }

    override suspend fun selectAll(): List<Plant> {
        return pushQuery{
            Plants.selectAll()
                .map(::resultRowToModel)
        }
    }

    override suspend fun selectSingle(where: SqlExpressionBuilder.() -> Op<Boolean>): Plant? {
        return pushQuery {
            Plants.select(where)
                .map(::resultRowToModel)
                .singleOrNull()
        }
    }

    override suspend fun selectMany(where: SqlExpressionBuilder.() -> Op<Boolean>): List<Plant> {
        return pushQuery {
            Plants.select(where)
                .map(::resultRowToModel)
        }
    }

    override suspend fun delete(where: SqlExpressionBuilder.() -> Op<Boolean>): Boolean {
        return pushQuery { Plants.deleteWhere(op=where) > 0 }
    }

    override suspend fun checkExist(model: Plant): Boolean {
        return selectSingle {
            (id eq model.id)
            .and(title eq model.title)
            .and(description eq model.description)
            .and(cover eq model.cover)
            .and(price eq model.price)
            .and(room eq model.room)
        } != null
    }

    override suspend fun insert(model: Plant): Plant? {
        return pushQuery {
            Plants.insert {
                it[title] = model.title
                it[description] = model.description
                it[cover] = model.cover
                it[price] = model.price
                it[room] = model.room
            }.resultedValues?.singleOrNull()?.let(::resultRowToModel)
        }
    }

    override suspend fun edit(model: Plant, where: SqlExpressionBuilder.() -> Op<Boolean>): Boolean {
        return pushQuery {
            Plants.update {
                it[title] = model.title
                it[description] = model.description
                it[cover] = model.cover
                it[price] = model.price
                it[room] = model.room
            }
        } > 0
    }
}