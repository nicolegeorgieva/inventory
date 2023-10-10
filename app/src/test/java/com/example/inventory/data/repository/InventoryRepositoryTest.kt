package com.example.inventory.data.repository

import com.example.inventory.data.database.entity.InventoryEntity
import com.example.inventory.data.datasource.InventoryDataSource
import com.example.inventory.data.model.InventoryItem
import com.example.inventory.data.repository.mapper.InventoryMapper
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
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
})