package com.cit.controllers

import com.cit.database.payment_methods.DAOPaymentMethod
import com.cit.database.payment_methods.PaymentMethod

class PaymentMethodsController {
    private val daoPaymentMethod = DAOPaymentMethod()

    suspend fun getMethods(): List<PaymentMethod>{
        return daoPaymentMethod.selectAll()
    }
}