package com.example.inventory.domain

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class IdProviderTest : FreeSpec({
    "generates unique id" {
        // given
        val idProvider = IdProvider()

        // when
        val id1 = idProvider.generateId()
        val id2 = idProvider.generateId()
        val areUnique = id1 != id2

        // then
        areUnique shouldBe true
    }
})