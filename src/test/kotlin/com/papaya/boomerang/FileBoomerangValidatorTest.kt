package com.papaya.boomerang

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class FileBoomerangValidatorTest {

    @Test
    fun passGivenPhrase(){
        FileBoomerangValidator.validateNotEmpty("shouldPass", "this should pass")
        //pass
    }

    @Test
    fun throwGivenEmptyPhrase(){
        Assertions.assertThrows(FileBoomerangException::class.java) {
            FileBoomerangValidator.validateNotEmpty("", "this should fail")
        }
    }

    @Test
    fun throwGivenInValidUrl(){
        val fail = Assertions.assertThrows(FileBoomerangException::class.java) {
            FileBoomerangValidator.validateInValidUrl("http//non-valid.com")
        }
        assertEquals("http//non-valid.com is not a valid url", fail.message)

    }
}