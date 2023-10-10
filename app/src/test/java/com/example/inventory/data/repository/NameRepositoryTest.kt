package com.example.inventory.data.repository

import com.example.inventory.data.datasource.NameDataSource
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk

class NameRepositoryTest : FreeSpec({
    val dataSource = mockk<NameDataSource>()
    fun newRepository(): NameRepository {
        return NameRepository(dataSource)
    }

    "get name" - {
        "existing name" {
            // given
            val repository = newRepository()
            coEvery { dataSource.getName() } returns "Amy"

            // when
            val name = repository.getName()

            // then
            name shouldBe "Amy"
        }

        "null name" {
            // given
            val repository = newRepository()
            coEvery { dataSource.getName() } returns null

            // when
            val name = repository.getName()

            // then
            name shouldBe null
        }
    }
})