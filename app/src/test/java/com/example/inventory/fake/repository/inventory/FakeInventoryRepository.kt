package com.example.inventory.fake.repository.inventory

import com.example.inventory.data.model.InventoryItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

class FakeInventoryRepository {
    private val items = mutableListOf<InventoryItem>()

    suspend fun getAll(): List<InventoryItem> {
        return withContext(Dispatchers.IO) {
            items
        }
    }

    suspend fun getAllByCategory(category: String): List<InventoryItem> {
        return withContext(Dispatchers.IO) {
            items.filter { it.category == category }
        }
    }

    suspend fun getById(id: UUID): InventoryItem? {
        return withContext(Dispatchers.IO) {
            items.find { it.id == id }
        }
    }

    suspend fun orderByAscending(): List<InventoryItem> {
        return withContext(Dispatchers.IO) {
            items.sortedBy { it.quantity }
        }
    }

    suspend fun orderByDescending(): List<InventoryItem> {
        return withContext(Dispatchers.IO) {
            items.sortedByDescending { it.quantity }
        }
    }

    suspend fun add(inventoryItem: InventoryItem) {
        val checkExistingName = getAll().filter { it.name == inventoryItem.name }
        if (checkExistingName.isNotEmpty()) return

        withContext(Dispatchers.IO) {
            items.add(inventoryItem)
        }
    }

    suspend fun update(inventoryItem: InventoryItem) {
        val item = getById(inventoryItem.id) ?: return

        withContext(Dispatchers.IO) {
            items.remove(item)
            items.add(inventoryItem)
        }
    }

    suspend fun delete(inventoryItem: InventoryItem) {
        withContext(Dispatchers.IO) {
            items.remove(inventoryItem)
        }
    }

    suspend fun deleteAll() {
        withContext(Dispatchers.IO) {
            items.clear()
        }
    }
}