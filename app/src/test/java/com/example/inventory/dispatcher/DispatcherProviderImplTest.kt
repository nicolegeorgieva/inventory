package com.example.inventory.dispatcher

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.Dispatchers

class DispatcherProviderImplTest : FreeSpec({
    // given
    val dispatcherProvider = DispatcherProviderImpl()

    "main" {
        // when
        val dispatcher = dispatcherProvider.main

        // then
        dispatcher shouldBe Dispatchers.Main
    }

    "io" {
        // when
        val dispatcher = dispatcherProvider.io

        // then
        dispatcher shouldBe Dispatchers.IO
    }

    "default" {
        // when
        val dispatcher = dispatcherProvider.default

        // then
        dispatcher shouldBe Dispatchers.Default
    }
})