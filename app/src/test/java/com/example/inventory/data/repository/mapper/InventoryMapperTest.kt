package com.example.inventory.data.repository.mapper

import com.example.inventory.data.database.entity.InventoryEntity
import com.example.inventory.data.model.InventoryItem
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import java.util.UUID

class InventoryMapperTest : FreeSpec({
    val inventoryMapper = InventoryMapper()

    "entity to domain" {
        // given
        val id = UUID.randomUUID()
        val entity = InventoryEntity(
            id = id,
            name = "Watter bottles",
            quantity = 5,
            minQuantityTarget = 5,
            category = "Groceries",
            description = null,
            image = null
        )

        // when
        val res = inventoryMapper.entityToDomain(entity)

        // then
        res shouldBe InventoryItem(
            id = id,
            name = "Watter bottles",
            quantity = 5,
            minQuantityTarget = 5,
            category = "Groceries",
            description = null,
            imageUrl = null
        )
    }

    "domain to entity" {
        // given
        val id = UUID.randomUUID()
        val domain = InventoryItem(
            id = id,
            name = "Watter bottles",
            quantity = 5,
            minQuantityTarget = 5,
            category = "Groceries",
            description = null,
            imageUrl = null
        )

        // when
        val res = inventoryMapper.domainToEntity(domain)

        // then
        res shouldBe InventoryEntity(
            id = id,
            name = "Watter bottles",
            quantity = 5,
            minQuantityTarget = 5,
            category = "Groceries",
            description = null,
            image = null
        )
    }
})