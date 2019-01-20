package com.papaya.boomerang.support

import org.rockm.blink.BlinkServer

object ResourceStub {

    var stub : BlinkServer = BlinkServer(8080)

    fun init() {
        stub.get("/resource.pdf") { _, _ -> readResource("/resource.pdf")}
        stub.get("/badResponse.pdf") { _, res -> res.status(500)}
    }

    fun readResource(path : String): ByteArray {
        return javaClass.getResource(path).readBytes()
    }

    fun teardown() {
        stub.stop()
    }
}