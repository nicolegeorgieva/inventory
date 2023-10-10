package com.example.inventory.data.repository

import com.example.inventory.data.database.entity.InventoryEntity
import com.example.inventory.data.datasource.InventoryDataSource
import com.example.inventory.data.model.InventoryItem
import com.example.inventory.data.repository.mapper.InventoryMapper
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
        val repository = InventoryRepository(dataSource, InventoryMapper())
        val id = UUID.randomUUID()
        val id2 = UUID.randomUUID()
        coEvery { dataSource.getAll() } returns listOf(
            InventoryEntity(
                id = id,
                name = "Watter bottles",
                quantity = 5,
                imageUrl = null,
                category = "Groceries"
            ),
            InventoryEntity(
                id = id2,
                name = "Kitchen paper",
                quantity = 4,
                imageUrl = null,
                category = "Groceries"
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
                imageUrl = null,
                category = "Groceries"
            ),
            InventoryItem(
                id = id2,
                name = "Kitchen paper",
                quantity = 4,
                imageUrl = null,
                category = "Groceries"
            )
        )
    }

    "get all by category" {
        // given
        val dataSource = mockk<InventoryDataSource>()
        val id = UUID.randomUUID()
        val repository = InventoryRepository(dataSource, InventoryMapper())
        coEvery { dataSource.getAllByCategory("Groceries") } returns listOf(
            InventoryEntity(
                id = id,
                name = "Watter bottles",
                quantity = 5,
                imageUrl = null,
                category = "Groceries"
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
                imageUrl = null,
                category = "Groceries"
            )
        )
    }

    "get by id" - {
        "existing inventory entity" {
            // given
            val dataSource = mockk<InventoryDataSource>()
            val repository = InventoryRepository(dataSource, InventoryMapper())
            val id = UUID.randomUUID()
            coEvery { dataSource.getById(id) } returns InventoryEntity(
                id = id,
                name = "Watter bottles",
                quantity = 5,
                imageUrl = null,
                category = "Groceries"
            )

            // when
            val inventoryItem = repository.getById(id)

            // then
            inventoryItem shouldBe InventoryItem(
                id = id,
                name = "Watter bottles",
                quantity = 5,
                imageUrl = null,
                category = "Groceries"
            )
        }

        "null from the DataSource" {
            // given
            val dataSource = mockk<InventoryDataSource>()
            val repository = InventoryRepository(dataSource, InventoryMapper())
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
        val repository = InventoryRepository(dataSource, InventoryMapper())
        val id = UUID.randomUUID()
        val id2 = UUID.randomUUID()
        coEvery { dataSource.orderByAscending() } returns listOf(
            InventoryEntity(
                id = id2,
                name = "Kitchen paper",
                quantity = 4,
                imageUrl = null,
                category = "Groceries"
            ),
            InventoryEntity(
                id = id,
                name = "Watter bottles",
                quantity = 5,
                imageUrl = null,
                category = "Groceries"
            )
        )

        // when
        val inventoryItems = repository.orderByAscending()

        // then
        inventoryItems shouldBe listOf(
            InventoryItem(
                id = id2,
                name = "Kitchen paper",
                quantity = 4,
                imageUrl = null,
                category = "Groceries"
            ),
            InventoryItem(
                id = id,
                name = "Watter bottles",
                quantity = 5,
                imageUrl = null,
                category = "Groceries"
            )
        )
    }

    "orderByDescending" {
        // given
        val dataSource = mockk<InventoryDataSource>()
        val repository = InventoryRepository(dataSource, InventoryMapper())
        val id = UUID.randomUUID()
        val id2 = UUID.randomUUID()
        coEvery { dataSource.orderByDescending() } returns listOf(
            InventoryEntity(
                id = id,
                name = "Watter bottles",
                quantity = 5,
                imageUrl = null,
                category = "Groceries"
            ),
            InventoryEntity(
                id = id2,
                name = "Kitchen paper",
                quantity = 4,
                imageUrl = null,
                category = "Groceries"
            )
        )

        // when
        val inventoryItems = repository.orderByDescending()

        // then
        inventoryItems shouldBe listOf(
            InventoryItem(
                id = id,
                name = "Watter bottles",
                quantity = 5,
                imageUrl = null,
                category = "Groceries"
            ),
            InventoryItem(
                id = id2,
                name = "Kitchen paper",
                quantity = 4,
                imageUrl = null,
                category = "Groceries"
            )
        )
    }

    "save" {
        // given
        val dataSource = mockk<InventoryDataSource>()
        val repository = InventoryRepository(dataSource, InventoryMapper())
        val id = UUID.randomUUID()
        val inventoryItem = InventoryItem(
            id = id,
            name = "Watter bottles",
            quantity = 5,
            imageUrl = null,
            category = "Groceries"
        )
        coEvery { dataSource.save(any()) } just runs

        // when
        repository.save(inventoryItem)

        // then
        coVerify(exactly = 1) {
            dataSource.save(
                InventoryEntity(
                    id = id,
                    name = "Watter bottles",
                    quantity = 5,
                    imageUrl = null,
                    category = "Groceries"
                )
            )
        }
    }
})