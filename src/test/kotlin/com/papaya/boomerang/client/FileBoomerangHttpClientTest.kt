package com.papaya.boomerang.client

import com.papaya.boomerang.validation.FileBoomerangException
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.rockm.blink.BlinkServer

class FileBoomerangHttpClientTest {

    @BeforeEach
    fun init(){
        FailureResourceStub.init()
    }

    @Test
    fun throwExceptionGivenBadResponse(){
        Assertions.assertThrows(FileBoomerangException::class.java) {
            FileBoomerangHttpClient.downloadResource("http//localhost:9090/shouldFail.pdf")
        }
    }

    object FailureResourceStub {

        val stub = BlinkServer(9090)

        fun init(){
            stub.get("/shouldFail.pdf") { _, res ->
                res.status(500)
            }
        }

        fun stop(){
            stub.stop()
        }
    }

    @AfterEach
    fun teardown(){
        FailureResourceStub.stop()
    }
}

