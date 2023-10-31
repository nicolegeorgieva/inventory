package com.example.inventory.fake.repository.inventory

import com.example.inventory.data.model.InventoryItem
import com.example.inventory.data.repository.inventory.InventoryRepository
import java.util.UUID

class FakeInventoryRepository : InventoryRepository {
    private val items = mutableListOf<InventoryItem>()
    override suspend fun getAll(): List<InventoryItem> {
        return items.toList()
    }

    override suspend fun getAllOrderedByAscending(): List<InventoryItem> {
        return items.sortedBy {
            it.quantity < it.minQuantityTarget
        }.toList()
    }

    override suspend fun getAllOrderedByDescending(): List<InventoryItem> {
        return items.sortedBy {
            it.quantity >= it.minQuantityTarget
        }.toList()
    }

    override suspend fun getAllByCategory(category: String): List<InventoryItem> {
        return items.filter { it.category == category }
    }

    override suspend fun getById(id: UUID): InventoryItem? {
        return items.find { it.id == id }
    }

    override suspend fun orderByAscending(category: String): List<InventoryItem> {
        return items.filter {
            it.category == category
        }.sortedBy {
            it.quantity < it.minQuantityTarget
        }.toList()
    }

    override suspend fun orderByDescending(category: String): List<InventoryItem> {
        return items.filter {
            it.category == category
        }.sortedBy {
            it.quantity >= it.minQuantityTarget
        }.toList()
    }

    override suspend fun add(inventoryItem: InventoryItem) {
        val checkExistingName = getAllOrderedByAscending().filter { it.name == inventoryItem.name }
        if (checkExistingName.isNotEmpty()) return

        items.add(inventoryItem)
    }

    override suspend fun update(inventoryItem: InventoryItem) {
        val item = getById(inventoryItem.id) ?: return

        items.remove(item)
        items.add(inventoryItem)
    }

    override suspend fun delete(inventoryItem: InventoryItem) {
        items.remove(inventoryItem)
    }

    override suspend fun deleteAll() {
        items.clear()
    }
}