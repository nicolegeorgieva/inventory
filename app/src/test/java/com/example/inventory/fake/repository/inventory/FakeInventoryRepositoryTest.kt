package com.example.inventory.fake.repository.inventory

import com.example.inventory.data.model.InventoryItem
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import java.util.UUID

class FakeInventoryRepositoryTest : FreeSpec({
    "getAll" - {
        "list of items" {
            // given
            val repository = FakeInventoryRepository()
            val id = UUID.randomUUID()
            val id2 = UUID.randomUUID()
            val inventoryItem = InventoryItem(
                id = id,
                name = "Kitchen roll",
                quantity = 4,
                minQuantityTarget = 5,
                category = "Groceries",
                description = "",
                imagePath = ""
            )
            val inventoryItem2 = InventoryItem(
                id = id2,
                name = "Water bottles",
                quantity = 5,
                minQuantityTarget = 5,
                category = "Groceries",
                description = "",
                imagePath = ""
            )

            // when
            repository.add(inventoryItem)
            repository.add(inventoryItem2)
            val items = repository.getAll()

            // then
            items shouldBe listOf(inventoryItem, inventoryItem2)
        }

        "empty list" {
            // given
            val repository = FakeInventoryRepository()

            // when
            val items = repository.getAll()

            // then
            items shouldBe emptyList()
        }
    }

    "getAllByCategory" {
        // given
        val repository = FakeInventoryRepository()
        val id = UUID.randomUUID()
        val id2 = UUID.randomUUID()
        val inventoryItem = InventoryItem(
            id = id,
            name = "Laptop",
            quantity = 1,
            minQuantityTarget = 1,
            category = "Tech",
            description = "",
            imagePath = ""
        )
        val inventoryItem2 = InventoryItem(
            id = id2,
            name = "Water bottles",
            quantity = 5,
            minQuantityTarget = 5,
            category = "Groceries",
            description = "",
            imagePath = ""
        )

        // when
        repository.add(inventoryItem)
        repository.add(inventoryItem2)
        val res = repository.getAllByCategory("Groceries")

        // then
        res shouldBe listOf(inventoryItem2)
    }

    "getById" - {
        "existing id" {
            // given
            val repository = FakeInventoryRepository()
            val id = UUID.randomUUID()
            val inventoryItem = InventoryItem(
                id = id,
                name = "Laptop",
                quantity = 1,
                minQuantityTarget = 1,
                category = "Tech",
                description = "",
                imagePath = ""
            )

            // when
            repository.add(inventoryItem)
            val item = repository.getById(id)

            // then
            item shouldBe inventoryItem
        }

        "not existing id" {
            // given
            val repository = FakeInventoryRepository()
            val id = UUID.randomUUID()
            val inventoryItem = InventoryItem(
                id = UUID.randomUUID(),
                name = "Laptop",
                quantity = 1,
                minQuantityTarget = 1,
                category = "Tech",
                description = "",
                imagePath = ""
            )

            // when
            repository.add(inventoryItem)
            val res = repository.getById(id)

            // then
            res shouldBe null
        }
    }
})