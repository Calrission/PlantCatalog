package com.cit.database.payment_methods

import com.cit.database.DAOTable
import com.cit.database.DatabaseFactory.pushQuery
import com.cit.database.payment_methods.PaymentMethods.cover
import com.cit.database.payment_methods.PaymentMethods.id
import com.cit.database.payment_methods.PaymentMethods.title
import org.jetbrains.exposed.sql.*

class DAOPaymentMethod: DAOTable<PaymentMethod> {
    override fun resultRowToModel(row: ResultRow): PaymentMethod {
        return PaymentMethod(
            id  = row[id],
            title = row[title],
            cover = row[cover],
        )
    }

    override suspend fun selectAll(): List<PaymentMethod> {
        return pushQuery{
            PaymentMethods.selectAll()
                .map(::resultRowToModel)
        }
    }

    override suspend fun selectSingle(where: SqlExpressionBuilder.() -> Op<Boolean>): PaymentMethod? {
        return pushQuery {
            PaymentMethods.select(where)
                .map(::resultRowToModel)
                .singleOrNull()
        }
    }

    override suspend fun selectMany(where: SqlExpressionBuilder.() -> Op<Boolean>): List<PaymentMethod> {
        return pushQuery {
            PaymentMethods.select(where)
                .map(::resultRowToModel)
        }
    }

    override suspend fun delete(where: SqlExpressionBuilder.() -> Op<Boolean>): Boolean {
        return pushQuery { PaymentMethods.deleteWhere(op=where) > 0 }
    }

    override suspend fun checkExist(model: PaymentMethod): Boolean {
        return selectSingle {
            (id eq model.id)
            .and(title eq model.title)
            .and(cover eq model.cover)
        } != null
    }

    override suspend fun insert(model: PaymentMethod): PaymentMethod? {
        return pushQuery {
            PaymentMethods.insert {
                it[title] = model.title
                it[cover] = model.cover
            }.resultedValues?.singleOrNull()?.let(::resultRowToModel)
        }
    }

    override suspend fun edit(model: PaymentMethod, where: SqlExpressionBuilder.() -> Op<Boolean>): Boolean {
        return pushQuery {
            PaymentMethods.update {
                it[title] = model.title
                it[cover] = model.cover
            }
        } > 0
    }
}