package com.papaya.boomerang

import com.papaya.boomerang.support.MockS3Client
import com.papaya.boomerang.support.ResourceStub
import org.junit.Rule
import org.junit.contrib.java.lang.system.EnvironmentVariables
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import java.io.InputStream

@TestInstance(PER_CLASS)
class FileBoomerangTest {

    private val resourceStub : ResourceStub = ResourceStub
    private val mockS3 : MockS3Client = MockS3Client

    @Rule
    val environmentVariables = EnvironmentVariables()

    @BeforeAll
    fun before(){
        environmentVariables.set("TEST", "true")
        resourceStub.init()
        mockS3.turnOn(8001)
        mockS3.client().createBucket("test")
    }

    @Test
    fun shipResourceToDestination(){
        FileBoomerang(FileBoomerangOptions("test", true)).resource("http://localhost:8080/resource.pdf").shipTo("/resource.pdf")
        assertResourceEqualsTo(uploadedFile())
    }

    @Test
    fun throwExceptionGivenEmptyBucket(){
        Assertions.assertThrows(FileBoomerangException::class.java) {
            FileBoomerang(FileBoomerangOptions("", true)).resource("http://localhost:8080/resource.pdf").shipTo("/resource.pdf")
        }
    }

    @Test
    fun throwExceptionGivenBadResponseOnResource(){
        Assertions.assertThrows(FileBoomerangException::class.java) {
            FileBoomerang(FileBoomerangOptions("test",true)).resource("http://localhost:8080/badResponse.pdf").shipTo("/resource.pdf")
        }
    }

    private fun assertResourceEqualsTo(stream: InputStream) {
        assertArrayEquals(stream.readBytes(), javaClass.getResource("/resource.pdf").readBytes())
    }

    private fun uploadedFile(): InputStream {
        return mockS3.client().getObject("test", "/resource.pdf").objectContent
    }

    @AfterAll
    fun teardown(){
        resourceStub.teardown()
        mockS3.turnOff()
    }
}