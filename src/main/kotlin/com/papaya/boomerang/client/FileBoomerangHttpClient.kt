package com.papaya.boomerang.client

import com.papaya.boomerang.FileBoomerangException
import org.apache.http.client.fluent.Request
import java.io.IOException
import java.io.InputStream
import java.util.logging.Logger

object FileBoomerangHttpClient {

    val logger = Logger.getLogger(FileBoomerangHttpClient::class.java.name)

    fun downloadResource(resourceToDownload: String): InputStream {
        try {
            logger.info("going to download $resourceToDownload to tmp")
            val result = Request.Get(resourceToDownload).execute().returnContent().asStream()
            logger.info("downloaded $resourceToDownload successfully to tmp")
            return result
        } catch (e: IOException) { // catch all .net error
            logger.severe("Failed to downloadResource $resourceToDownload, follow reason: $e")
        }

        throw FileBoomerangException("Failed to downloadResource $resourceToDownload" )
    }
}