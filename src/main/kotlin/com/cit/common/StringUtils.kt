package com.cit.common

import java.util.regex.Pattern

class StringUtils {
    companion object {
        val EMAIL_ADDRESS_PATTERN: Pattern = Pattern.compile(
            "[a-z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-z0-9][a-z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-z][a-z\\-]{0,2}" +
                    ")+"
        )

        fun String.validEmail(): Boolean{
            return EMAIL_ADDRESS_PATTERN.matcher(this).matches()
        }

        fun String.validPassword(): Boolean{
            return length >= 8
        }
    }
}