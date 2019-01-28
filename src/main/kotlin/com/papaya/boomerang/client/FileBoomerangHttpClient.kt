package com.papaya.boomerang.client

import com.papaya.boomerang.FileBoomerangException
import com.papaya.boomerang.FileBoomerangLogger
import com.papaya.boomerang.FileBoomerangLogger.logger
import org.apache.http.client.fluent.Request
import java.io.IOException
import java.io.InputStream

object FileBoomerangHttpClient {

    fun downloadResource(resourceToDownload: String): InputStream {
        try {
            logger.info("going to download $resourceToDownload to tmp")
            val result = Request.Get(resourceToDownload).execute().returnContent().asStream()
            logger.info("downloaded $resourceToDownload successfully to tmp")
            return result
        } catch (e: IOException) { // catch all .net error
            logger.error("Failed to downloadResource $resourceToDownload, follow reason: $e")
        }

        throw FileBoomerangException("Failed to downloadResource $resourceToDownload" )
    }
}