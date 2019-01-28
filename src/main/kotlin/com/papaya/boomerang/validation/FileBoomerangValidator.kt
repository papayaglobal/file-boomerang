package com.papaya.boomerang.validation

import java.net.URL

object FileBoomerangValidator {

    private inline fun throwIf(condition: Boolean, thr: () -> Throwable) {
        if(condition) {
            throw thr()
        }
    }

    fun validateNotEmpty(toValidate: String, msg: String) {
        throwIf(toValidate.isBlank()) { throw FileBoomerangException(msg) }
    }

    fun validateInValidUrl(urlToValidate: String) {
        try {
            URL(urlToValidate).toURI()
        } catch (e: Exception) {
            throw FileBoomerangException("$urlToValidate is not a valid url")
        }
    }
}