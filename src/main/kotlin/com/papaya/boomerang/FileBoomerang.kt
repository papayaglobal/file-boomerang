package com.papaya.boomerang

import com.papaya.boomerang.FileBoomerangValidator.validateInValidUrl
import com.papaya.boomerang.FileBoomerangValidator.validateNotEmpty
import com.papaya.boomerang.client.AwsClient
import com.papaya.boomerang.client.FileBoomerangHttpClient

class FileBoomerang(val options: FileBoomerangOptions) {

    private var resource : String = ""

    @Throws(FileBoomerangException::class)
    fun resource(resource: String): FileBoomerang {
        validateNotEmpty(resource, "resource path cannot be empty")
        validateInValidUrl(resource)
        this.resource = resource
        return this
    }

    @Throws(FileBoomerangException::class)
    fun shipTo(destination: String)  {
        validateNotEmpty(destination, "destination path cannot be empty")
        val resource = FileBoomerangHttpClient.downloadResource(resource)
        AwsClient.uploadResourceTo(options.bucket,resource, destination)
    }

}
