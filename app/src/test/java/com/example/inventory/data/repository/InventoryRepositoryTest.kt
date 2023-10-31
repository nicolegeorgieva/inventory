package com.example.inventory.data.repository

import com.example.inventory.data.database.entity.InventoryEntity
import com.example.inventory.data.datasource.InventoryDataSource
import com.example.inventory.data.model.InventoryItem
import com.example.inventory.data.repository.inventory.InventoryRepositoryImpl
import com.example.inventory.data.repository.mapper.InventoryMapper
import com.example.inventory.fake.dispatcher.FakeDispatcherProvider
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import java.util.UUID

class InventoryRepositoryTest : FreeSpec({
    "get all" {
        // given
        val dataSource = mockk<InventoryDataSource>()
        val dispatchers = FakeDispatcherProvider()
        val repository = InventoryRepositoryImpl(dataSource, InventoryMapper(), dispatchers)
        val id = UUID.randomUUID()
        val id2 = UUID.randomUUID()
        coEvery { dataSource.getAll() } returns listOf(
            InventoryEntity(
                id = id,
                name = "Watter bottles",
                quantity = 5,
                minQuantityTarget = 5,
                category = "Groceries",
                description = null,
                imagePath = null
            ),
            InventoryEntity(
                id = id2,
                name = "Kitchen paper",
                quantity = 4,
                minQuantityTarget = 5,
                category = "Groceries",
                description = null,
                imagePath = null
            )
        )

        // when
        val inventoryItems = repository.getAll()

        // then
        inventoryItems shouldBe listOf(
            InventoryItem(
                id = id,
                name = "Watter bottles",
                quantity = 5,
                minQuantityTarget = 5,
                category = "Groceries",
                description = null,
                imagePath = null
            ),
            InventoryItem(
                id = id2,
                name = "Kitchen paper",
                quantity = 4,
                minQuantityTarget = 5,
                category = "Groceries",
                description = null,
                imagePath = null
            )
        )
    }

    "get all ordered by ascending" {
        // given
        val dataSource = mockk<InventoryDataSource>()
        val dispatchers = FakeDispatcherProvider()
        val repository = InventoryRepositoryImpl(dataSource, InventoryMapper(), dispatchers)
        val id = UUID.randomUUID()
        val id2 = UUID.randomUUID()
        coEvery { dataSource.getAllOrderedByAscending() } returns listOf(
            InventoryEntity(
                id = id,
                name = "Watter bottles",
                quantity = 1,
                minQuantityTarget = 5,
                category = "Groceries",
                description = null,
                imagePath = null
            ),
            InventoryEntity(
                id = id2,
                name = "Kitchen paper",
                quantity = 4,
                minQuantityTarget = 5,
                category = "Groceries",
                description = null,
                imagePath = null
            )
        )

        // when
        val inventoryList = repository.getAllOrderedByAscending()

        // then
        inventoryList shouldBe listOf(
            InventoryItem(
                id = id,
                name = "Watter bottles",
                quantity = 1,
                minQuantityTarget = 5,
                category = "Groceries",
                description = null,
                imagePath = null
            ),
            InventoryItem(
                id = id2,
                name = "Kitchen paper",
                quantity = 4,
                minQuantityTarget = 5,
                category = "Groceries",
                description = null,
                imagePath = null
            )
        )
    }

    "get all ordered by descending" {

    }

    "get all by category" {
        // given
        val dataSource = mockk<InventoryDataSource>()
        val dispatchers = FakeDispatcherProvider()
        val id = UUID.randomUUID()
        val repository = InventoryRepositoryImpl(dataSource, InventoryMapper(), dispatchers)
        coEvery { dataSource.getAllByCategory("Groceries") } returns listOf(
            InventoryEntity(
                id = id,
                name = "Watter bottles",
                quantity = 5,
                minQuantityTarget = 5,
                category = "Groceries",
                description = null,
                imagePath = null
            )
        )

        // when
        val res = repository.getAllByCategory("Groceries")

        // then
        res shouldBe listOf(
            InventoryItem(
                id = id,
                name = "Watter bottles",
                quantity = 5,
                minQuantityTarget = 5,
                category = "Groceries",
                description = null,
                imagePath = null
            )
        )
    }

    "get by id" - {
        "existing inventory entity" {
            // given
            val dataSource = mockk<InventoryDataSource>()
            val dispatchers = FakeDispatcherProvider()
            val repository = InventoryRepositoryImpl(dataSource, InventoryMapper(), dispatchers)
            val id = UUID.randomUUID()
            coEvery { dataSource.getById(id) } returns InventoryEntity(
                id = id,
                name = "Watter bottles",
                quantity = 5,
                minQuantityTarget = 5,
                category = "Groceries",
                description = null,
                imagePath = null
            )

            // when
            val inventoryItem = repository.getById(id)

            // then
            inventoryItem shouldBe InventoryItem(
                id = id,
                name = "Watter bottles",
                quantity = 5,
                minQuantityTarget = 5,
                category = "Groceries",
                description = null,
                imagePath = null
            )
        }

        "null from the DataSource" {
            // given
            val dataSource = mockk<InventoryDataSource>()
            val dispatchers = FakeDispatcherProvider()
            val repository = InventoryRepositoryImpl(dataSource, InventoryMapper(), dispatchers)
            val id = UUID.randomUUID()
            coEvery { dataSource.getById(id) } returns null

            // when
            val res = repository.getById(id)

            // then
            res shouldBe null
        }
    }

    "orderByAscending" {
        // given
        val dataSource = mockk<InventoryDataSource>()
        val dispatchers = FakeDispatcherProvider()
        val repository = InventoryRepositoryImpl(dataSource, InventoryMapper(), dispatchers)
        val id = UUID.randomUUID()
        val id2 = UUID.randomUUID()
        coEvery { dataSource.orderByAscending(category = "Groceries") } returns listOf(
            InventoryEntity(
                id = id2,
                name = "Kitchen paper",
                quantity = 4,
                minQuantityTarget = 5,
                category = "Groceries",
                description = null,
                imagePath = null
            ),
            InventoryEntity(
                id = id,
                name = "Watter bottles",
                quantity = 5,
                minQuantityTarget = 5,
                category = "Groceries",
                description = null,
                imagePath = null
            )
        )

        // when
        val inventoryItems = repository.orderByAscending("Groceries")

        // then
        inventoryItems shouldBe listOf(
            InventoryItem(
                id = id2,
                name = "Kitchen paper",
                quantity = 4,
                minQuantityTarget = 5,
                category = "Groceries",
                description = null,
                imagePath = null
            ),
            InventoryItem(
                id = id,
                name = "Watter bottles",
                quantity = 5,
                minQuantityTarget = 5,
                category = "Groceries",
                description = null,
                imagePath = null
            )
        )
    }

    "orderByDescending" {
        // given
        val dataSource = mockk<InventoryDataSource>()
        val dispatchers = FakeDispatcherProvider()
        val repository = InventoryRepositoryImpl(dataSource, InventoryMapper(), dispatchers)
        val id = UUID.randomUUID()
        val id2 = UUID.randomUUID()
        coEvery { dataSource.orderByDescending("Groceries") } returns listOf(
            InventoryEntity(
                id = id,
                name = "Watter bottles",
                quantity = 5,
                minQuantityTarget = 5,
                category = "Groceries",
                description = null,
                imagePath = null
            ),
            InventoryEntity(
                id = id2,
                name = "Kitchen paper",
                quantity = 4,
                minQuantityTarget = 5,
                category = "Groceries",
                description = null,
                imagePath = null
            )
        )

        // when
        val inventoryItems = repository.orderByDescending("Groceries")

        // then
        inventoryItems shouldBe listOf(
            InventoryItem(
                id = id,
                name = "Watter bottles",
                quantity = 5,
                minQuantityTarget = 5,
                category = "Groceries",
                description = null,
                imagePath = null
            ),
            InventoryItem(
                id = id2,
                name = "Kitchen paper",
                quantity = 4,
                minQuantityTarget = 5,
                category = "Groceries",
                description = null,
                imagePath = null
            )
        )
    }

    "add" - {
        "unique item" {
            // given
            val dataSource = mockk<InventoryDataSource>()
            val dispatchers = FakeDispatcherProvider()
            val repository = InventoryRepositoryImpl(dataSource, InventoryMapper(), dispatchers)
            val id = UUID.randomUUID()
            val inventoryItem = InventoryItem(
                id = id,
                name = "Watter bottles",
                quantity = 5,
                minQuantityTarget = 5,
                category = "Groceries",
                description = null,
                imagePath = null
            )
            coEvery { dataSource.getAll() } returns listOf(
                InventoryEntity(
                    id = UUID.randomUUID(),
                    name = "Kitchen roll",
                    quantity = 4,
                    minQuantityTarget = 4,
                    category = "Groceries",
                    description = null,
                    imagePath = null
                )
            )
            coEvery { dataSource.save(any()) } just runs

            // when
            repository.add(inventoryItem)

            // then
            coVerify(exactly = 1) {
                dataSource.save(
                    InventoryEntity(
                        id = id,
                        name = "Watter bottles",
                        quantity = 5,
                        minQuantityTarget = 5,
                        category = "Groceries",
                        description = null,
                        imagePath = null
                    )
                )
            }
        }

        "existing item name" {
            // given
            val dataSource = mockk<InventoryDataSource>()
            val dispatchers = FakeDispatcherProvider()
            val repository = InventoryRepositoryImpl(dataSource, InventoryMapper(), dispatchers)
            val id = UUID.randomUUID()
            val inventoryItem = InventoryItem(
                id = id,
                name = "Watter bottles",
                quantity = 5,
                minQuantityTarget = 5,
                category = "Groceries",
                description = null,
                imagePath = null
            )
            val inventoryEntity = InventoryEntity(
                id = id,
                name = "Watter bottles",
                quantity = 5,
                minQuantityTarget = 5,
                category = "Groceries",
                description = null,
                imagePath = null
            )
            coEvery { dataSource.getAll() } returns listOf(inventoryEntity)
            coEvery { dataSource.save(any()) } just runs

            // when
            repository.add(inventoryItem)

            // then
            coVerify(exactly = 0) {
                dataSource.save(any())
            }
        }
    }

    "update" - {
        "existing id" {
            // given
            val dataSource = mockk<InventoryDataSource>()
            val dispatchers = FakeDispatcherProvider()
            val repository = InventoryRepositoryImpl(dataSource, InventoryMapper(), dispatchers)
            val id = UUID.randomUUID()
            val inventoryItem = InventoryItem(
                id = id,
                name = "Watter bottles",
                quantity = 5,
                minQuantityTarget = 5,
                category = "Groceries",
                description = null,
                imagePath = null
            )
            val inventoryEntity = InventoryEntity(
                id = id,
                name = "Watter bottles",
                quantity = 5,
                minQuantityTarget = 5,
                category = "Groceries",
                description = null,
                imagePath = null
            )
            coEvery { dataSource.save(any()) } just runs
            coEvery { dataSource.getById(id) } returns inventoryEntity

            // when
            repository.update(inventoryItem)

            // then
            coVerify(exactly = 1) {
                dataSource.save(inventoryEntity)
            }
        }

        "not existing id" {
            // given
            val dataSource = mockk<InventoryDataSource>()
            val dispatchers = FakeDispatcherProvider()
            val repository = InventoryRepositoryImpl(dataSource, InventoryMapper(), dispatchers)
            val id = UUID.randomUUID()
            val inventoryItem = InventoryItem(
                id = id,
                name = "Watter bottles",
                quantity = 5,
                minQuantityTarget = 5,
                category = "Groceries",
                description = null,
                imagePath = null
            )
            val inventoryEntity = InventoryEntity(
                id = id,
                name = "Watter bottles",
                quantity = 5,
                minQuantityTarget = 5,
                category = "Groceries",
                description = null,
                imagePath = null
            )
            coEvery { dataSource.save(any()) } just runs
            coEvery { dataSource.getById(id) } returns null

            // when
            repository.update(inventoryItem)

            // then
            coVerify(exactly = 0) {
                dataSource.save(inventoryEntity)
            }
        }
    }

    "delete" {
        // given
        val dataSource = mockk<InventoryDataSource>()
        val dispatchers = FakeDispatcherProvider()
        val repository = InventoryRepositoryImpl(dataSource, InventoryMapper(), dispatchers)
        val id = UUID.randomUUID()
        val inventoryItem = InventoryItem(
            id = id,
            name = "Watter bottles",
            quantity = 5,
            minQuantityTarget = 5,
            category = "Groceries",
            description = null,
            imagePath = null
        )
        coEvery { dataSource.delete(any()) } just runs

        // when
        repository.delete(inventoryItem)

        // then
        coVerify(exactly = 1) {
            dataSource.delete(
                InventoryEntity(
                    id = id,
                    name = "Watter bottles",
                    quantity = 5,
                    minQuantityTarget = 5,
                    category = "Groceries",
                    description = null,
                    imagePath = null
                )
            )
        }
    }

    "delete all" {
        // given
        val dataSource = mockk<InventoryDataSource>()
        val dispatchers = FakeDispatcherProvider()
        val repository = InventoryRepositoryImpl(dataSource, InventoryMapper(), dispatchers)
        coEvery { dataSource.deleteAll() } just runs

        // when
        repository.deleteAll()

        // then
        coVerify(exactly = 1) {
            dataSource.deleteAll()
        }
    }
})