package com.papaya.boomerang.support

import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration
import io.findify.s3mock.S3Mock
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.AnonymousAWSCredentials
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder


object MockS3Client {

    private lateinit var api : S3Mock
    private var endpoint : EndpointConfiguration? = null

    fun turnOn(port : Int){
        api = S3Mock.Builder().withPort(port).withInMemoryBackend().build()
        endpoint = EndpointConfiguration("http://localhost:$port", "us-west-2")
        api.start()
    }

    fun client(): AmazonS3 {
        return AmazonS3ClientBuilder
                .standard()
                .withPathStyleAccessEnabled(true)
                .withEndpointConfiguration(endpoint)
                .withCredentials(AWSStaticCredentialsProvider(AnonymousAWSCredentials()))
                .build()
    }

    fun turnOff(){
        api.shutdown()
    }
}