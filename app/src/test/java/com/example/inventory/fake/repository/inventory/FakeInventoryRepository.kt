package com.example.inventory.fake.repository.inventory

import com.example.inventory.data.model.InventoryItem
import com.example.inventory.data.repository.inventory.InventoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

class FakeInventoryRepository : InventoryRepository {
    private val items = mutableListOf<InventoryItem>()

    override suspend fun getAll(): List<InventoryItem> {
        return withContext(Dispatchers.IO) {
            items
        }
    }

    override suspend fun getAllByCategory(category: String): List<InventoryItem> {
        return withContext(Dispatchers.IO) {
            items.filter { it.category == category }
        }
    }

    override suspend fun getById(id: UUID): InventoryItem? {
        return withContext(Dispatchers.IO) {
            items.find { it.id == id }
        }
    }

    override suspend fun orderByAscending(): List<InventoryItem> {
        return withContext(Dispatchers.IO) {
            items.sortedBy { it.quantity }
        }
    }

    override suspend fun orderByDescending(): List<InventoryItem> {
        return withContext(Dispatchers.IO) {
            items.sortedByDescending { it.quantity }
        }
    }

    override suspend fun add(inventoryItem: InventoryItem) {
        val checkExistingName = getAll().filter { it.name == inventoryItem.name }
        if (checkExistingName.isNotEmpty()) return

        withContext(Dispatchers.IO) {
            items.add(inventoryItem)
        }
    }

    override suspend fun update(inventoryItem: InventoryItem) {
        val item = getById(inventoryItem.id) ?: return

        withContext(Dispatchers.IO) {
            items.remove(item)
            items.add(inventoryItem)
        }
    }

    override suspend fun delete(inventoryItem: InventoryItem) {
        withContext(Dispatchers.IO) {
            items.remove(inventoryItem)
        }
    }

    override suspend fun deleteAll() {
        withContext(Dispatchers.IO) {
            items.clear()
        }
    }
}