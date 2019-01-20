package com.papaya.boomerang.common

class PropertyResolver(val propertyName : String) {

    fun resolve(): String? {
        return System.getenv(this.propertyName)
    }
}