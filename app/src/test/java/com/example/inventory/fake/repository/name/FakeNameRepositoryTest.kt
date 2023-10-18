package com.example.inventory.fake.repository.name

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class FakeNameRepositoryTest : FreeSpec({
    "get name" - {
        "name exists" {
            // given
            val repository = FakeNameRepository()

            // when
            repository.setName("Amy")
            val name = repository.getName()

            // then
            name shouldBe "Amy"
        }

        "name is null" {
            // given
            val repository = FakeNameRepository()

            // when
            val name = repository.getName()

            // then
            name shouldBe null
        }
    }

    "set name" - {
        "blank new name" {
            // given
            val repository = FakeNameRepository()

            // when
            repository.setName("")
            val name = repository.getName()

            // then
            name shouldBe null
        }

        "no blank name" {
            // given
            val repository = FakeNameRepository()

            // when
            repository.setName("   Amy   ")
            val name = repository.getName()

            // then
            name shouldBe "Amy"
        }
    }

    "remove name" {
        // given
        val repository = FakeNameRepository()

        // when
        repository.setName("Amy")
        repository.removeName()
        val name = repository.getName()

        // then
        name shouldBe null
    }
})