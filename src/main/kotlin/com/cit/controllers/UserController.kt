package com.cit.controllers

import com.cit.common.StringUtils.Companion.validEmail
import com.cit.common.StringUtils.Companion.validPassword
import com.cit.database.user.DAOUser
import com.cit.models.ModelError
import com.cit.models.ModelToken
import com.cit.models.SignInBody
import com.cit.models.SignUpBody

class UserController {

    private val daoUser = DAOUser()

    suspend fun signIn(body: SignInBody): Any {
        if (!body.password.validPassword()) return ModelError("This password not correct (min 8 length)")
        if (!daoUser.checkExist(body)) return ModelError("User not found")
        val newToken = daoUser.generateNewTokenUser(body.login) ?: return ModelError("New token not generated")
        return ModelToken(newToken)
    }

    suspend fun signUp(body: SignUpBody): Any{
        if (!body.password.validPassword()) return ModelError("This password not correct (min 8 length)")
        if (!body.email.validEmail()) return ModelError("This email not correct")
        return if (!daoUser.checkExistUserLogin(body.login) && !daoUser.checkExistUserEmail(body.email)){
            val user = daoUser.signUp(body) ?: return ModelError("User not created")
            ModelToken(user.token)
        }else{
            ModelError("This email or login already use")
        }
    }
}