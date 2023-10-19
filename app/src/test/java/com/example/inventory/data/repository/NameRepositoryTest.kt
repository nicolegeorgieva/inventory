package com.example.inventory.data.repository

import com.example.inventory.DispatcherProvider
import com.example.inventory.FakeDispatcherProvider
import com.example.inventory.data.datasource.NameDataSource
import com.example.inventory.data.repository.name.NameRepositoryImpl
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs

class NameRepositoryTest : FreeSpec({
    "get name" - {
        "existing name" {
            // given
            val dataSource = mockk<NameDataSource>()
            val dispatchers: DispatcherProvider = FakeDispatcherProvider()
            val repository = NameRepositoryImpl(dataSource, dispatchers)
            coEvery { dataSource.getName() } returns "Amy"

            // when
            val name = repository.getName()

            // then
            name shouldBe "Amy"
        }

        "null name" {
            // given
            val dataSource = mockk<NameDataSource>()
            val dispatchers: DispatcherProvider = FakeDispatcherProvider()
            val repository = NameRepositoryImpl(dataSource, dispatchers)
            coEvery { dataSource.getName() } returns null

            // when
            val name = repository.getName()

            // then
            name shouldBe null
        }
    }

    "set name" - {
        "valid name" {
            // given
            val dataSource = mockk<NameDataSource>()
            val dispatchers: DispatcherProvider = FakeDispatcherProvider()
            val repository = NameRepositoryImpl(dataSource, dispatchers)
            coEvery { dataSource.setName(any()) } just runs

            // when
            repository.setName("  Amy  ")

            // then
            coVerify(exactly = 1) {
                dataSource.setName("Amy")
            }
        }

        "invalid name" {
            // given
            val dataSource = mockk<NameDataSource>()
            val dispatchers: DispatcherProvider = FakeDispatcherProvider()
            val repository = NameRepositoryImpl(dataSource, dispatchers)
            coEvery { dataSource.setName(any()) } just runs

            // when
            repository.setName(" ")

            // then
            coVerify(exactly = 0) {
                dataSource.setName(any())
            }
        }
    }

    "remove name" {
        // given
        val dataSource = mockk<NameDataSource>()
        val dispatchers: DispatcherProvider = FakeDispatcherProvider()
        val repository = NameRepositoryImpl(dataSource, dispatchers)
        coEvery { dataSource.removeName() } just runs

        // when
        repository.removeName()

        // then
        coVerify(exactly = 1) {
            dataSource.removeName()
        }
    }
})