package com.papaya.boomerang.common

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Rule
import org.junit.contrib.java.lang.system.EnvironmentVariables
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class PropertyResolverTest {

    private val ENV_KEY = "env"
    private val ENV_VALUE = "test"

    @Rule
    val environmentVariables = EnvironmentVariables()

    @BeforeEach
    fun init() {
        environmentVariables.set(ENV_KEY, ENV_VALUE)
    }

    @Test
    fun resolveProperty() {
        assertThat(PropertyResolver(ENV_KEY).resolve(), `is`(ENV_VALUE))
    }

    @Test
    fun resolveEmptyPropertyWhenEvnNotFound() {
        assertNull(PropertyResolver("someKey").resolve())
    }
}