package com.example.inventory.fake.repository.inventory

import com.example.inventory.data.model.InventoryItem
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import java.util.UUID

class FakeInventoryRepositoryTest : FreeSpec({
    "get all" - {
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

        }
    }
})