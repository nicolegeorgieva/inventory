package com.example.inventory.data.repository.inventory

import com.example.inventory.data.model.InventoryItem
import java.util.UUID

interface InventoryRepository {
    suspend fun getAll(): List<InventoryItem>
    suspend fun getAllOrderedByAscending(): List<InventoryItem>
    suspend fun getAllOrderedByDescending(): List<InventoryItem>
    suspend fun getAllByCategory(category: String): List<InventoryItem>
    suspend fun getById(id: UUID): InventoryItem?
    suspend fun orderByAscending(category: String): List<InventoryItem>
    suspend fun orderByDescending(category: String): List<InventoryItem>
    suspend fun add(inventoryItem: InventoryItem)
    suspend fun update(inventoryItem: InventoryItem)
    suspend fun delete(inventoryItem: InventoryItem)
    suspend fun deleteAll()
}