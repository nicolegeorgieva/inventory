package com.example.inventory.fake.repository.inventory

import com.example.inventory.data.model.InventoryItem
import com.example.inventory.data.repository.inventory.InventoryRepository
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import java.util.UUID

class FakeInventoryRepositoryTest : FreeSpec({
    "getAll" - {
        "list of items" {
            // given
            val repository: InventoryRepository = FakeInventoryRepository()
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
            val repository: InventoryRepository = FakeInventoryRepository()

            // when
            val items = repository.getAll()

            // then
            items shouldBe emptyList()
        }
    }

    "getAllByCategory" {
        // given
        val repository: InventoryRepository = FakeInventoryRepository()
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
            val repository: InventoryRepository = FakeInventoryRepository()
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
            val repository: InventoryRepository = FakeInventoryRepository()
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

    "orderByAscending" {
        // given
        val repository: InventoryRepository = FakeInventoryRepository()
        val id = UUID.randomUUID()
        val id2 = UUID.randomUUID()
        val inventoryItem = InventoryItem(
            id = id,
            name = "Water bottles",
            quantity = 8,
            minQuantityTarget = 9,
            category = "Groceries",
            description = "",
            imagePath = ""
        )
        val inventoryItem2 = InventoryItem(
            id = id2,
            name = "Kitchen roll",
            quantity = 1,
            minQuantityTarget = 10,
            category = "Groceries",
            description = "",
            imagePath = ""
        )

        // when
        repository.add(inventoryItem)
        repository.add(inventoryItem2)
        val items = repository.orderByAscending("Groceries")

        // then
        items shouldBe listOf(inventoryItem, inventoryItem2)
    }

    "orderByDescending" {
        // given
        val repository: InventoryRepository = FakeInventoryRepository()
        val id = UUID.randomUUID()
        val id2 = UUID.randomUUID()
        val inventoryItem = InventoryItem(
            id = id,
            name = "Kitchen roll",
            quantity = 1,
            minQuantityTarget = 2,
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
        val items = repository.orderByDescending("Groceries")

        // then
        items shouldBe listOf(inventoryItem, inventoryItem2)
    }

    "add" - {
        "item with existing name" {
            // given
            val repository: InventoryRepository = FakeInventoryRepository()
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
            val inventoryItem2 = inventoryItem.copy(id = UUID.randomUUID(), quantity = 2)

            // when
            repository.add(inventoryItem)
            repository.add(inventoryItem2)
            val items = repository.getAll()

            // then
            items shouldBe listOf(inventoryItem)
        }

        "unique item" {
            // given
            val repository: InventoryRepository = FakeInventoryRepository()
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
            val inventoryItem2 = inventoryItem.copy(
                id = UUID.randomUUID(),
                name = "Kitchen paper",
                quantity = 2
            )

            // when
            repository.add(inventoryItem)
            repository.add(inventoryItem2)
            val items = repository.getAll()

            // then
            items shouldBe listOf(inventoryItem, inventoryItem2)
        }
    }

    "update" - {
        "not existing id" {
            // given
            val repository: InventoryRepository = FakeInventoryRepository()
            val id = UUID.randomUUID()
            val inventoryItem = InventoryItem(
                id = id,
                name = "Water bottles",
                quantity = 5,
                minQuantityTarget = 5,
                category = "Groceries",
                description = "",
                imagePath = ""
            )
            val inventoryItem2 = inventoryItem.copy(id = UUID.randomUUID())

            // when
            repository.add(inventoryItem2)
            repository.update(inventoryItem)
            val inventoryList = repository.getAll()

            // then
            inventoryList shouldBe listOf(inventoryItem2)
        }

        "existing id" {
            // given
            val repository: InventoryRepository = FakeInventoryRepository()
            val id = UUID.randomUUID()
            val inventoryItem = InventoryItem(
                id = id,
                name = "Water bottles",
                quantity = 5,
                minQuantityTarget = 5,
                category = "Groceries",
                description = "",
                imagePath = ""
            )
            val new = inventoryItem.copy(quantity = 3)

            // when
            repository.add(inventoryItem)
            repository.update(new)
            val item = repository.getById(id)

            // then
            item shouldBe new
        }
    }

    "delete" {
        // given
        val repository: InventoryRepository = FakeInventoryRepository()
        val id = UUID.randomUUID()
        val inventoryItem = InventoryItem(
            id = id,
            name = "Water bottles",
            quantity = 5,
            minQuantityTarget = 5,
            category = "Groceries",
            description = "",
            imagePath = ""
        )

        // when
        repository.add(inventoryItem)
        repository.delete(inventoryItem)
        val items = repository.getAll()

        // then
        items shouldBe emptyList()
    }

    "deleteAll" {
        // given
        val repository: InventoryRepository = FakeInventoryRepository()
        val id = UUID.randomUUID()
        val id2 = UUID.randomUUID()
        val inventoryItem = InventoryItem(
            id = id,
            name = "Water bottles",
            quantity = 5,
            minQuantityTarget = 5,
            category = "Groceries",
            description = "",
            imagePath = ""
        )
        val inventoryItem2 = InventoryItem(
            id = id2,
            name = "Kitchen roll",
            quantity = 4,
            minQuantityTarget = 5,
            category = "Groceries",
            description = "",
            imagePath = ""
        )

        // when
        repository.add(inventoryItem)
        repository.add(inventoryItem2)
        repository.deleteAll()
        val items = repository.getAll()

        // then
        items shouldBe emptyList()
    }
})