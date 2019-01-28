package com.papaya.boomerang.client

import com.amazonaws.SdkBaseException
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.AnonymousAWSCredentials
import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectResult
import com.papaya.boomerang.validation.FileBoomerangException
import com.papaya.boomerang.logger.FileBoomerangLogger.logger
import com.papaya.boomerang.validation.FileBoomerangValidator.validateNotEmpty
import com.papaya.boomerang.common.PropertyResolver
import java.io.InputStream


object AwsClient {


    private fun client(): AmazonS3 {
        return when(PropertyResolver("TEST").resolve()){
            null -> productionMode()
            else -> testMode()
        }
    }

    private fun productionMode(): AmazonS3 {
        logger.info("bootstrap production mode")
        return AmazonS3ClientBuilder.standard().
                withCredentials(ProfileCredentialsProvider()).
                build()
    }

    private fun testMode(): AmazonS3 {
        logger.info("bootstrap test mode")
        return AmazonS3ClientBuilder.standard().
                withPathStyleAccessEnabled(true).
                withEndpointConfiguration(AwsClientBuilder.EndpointConfiguration("http://localhost:8001", "us-west-2")).
                withCredentials(AWSStaticCredentialsProvider(AnonymousAWSCredentials())).
                build()
    }

    fun uploadResourceTo(bucket: String, resource: InputStream, destination: String) {
        validateNotEmpty(bucket, "bucket must not be empty")
        upload(bucket, destination, resource)
    }

    private fun upload(bucket: String, destination: String, resource: InputStream): PutObjectResult {
        try {
            logger.info("going to upload: $destination to bucket: $bucket")
            val result = client().putObject(bucket, destination, resource, stateContentLength(resource))
            logger.info("uploaded successfully $destination to bucket $bucket")
            return result
        } catch (e : SdkBaseException){
            logger.error("failed to upload $destination to bucket: $bucket, follow reason: $e")
        }

        throw FileBoomerangException("Failed to upload $destination to bucket: $bucket")
    }

    private fun stateContentLength(resource: InputStream): ObjectMetadata {
        val objectMetadata = ObjectMetadata()
        objectMetadata.contentLength = resource.available().toLong()
        return objectMetadata
    }
}