package com.example.inventory.data.repository.inventory

import com.example.inventory.data.model.InventoryItem
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface InventoryRepository {
    fun getAll(): Flow<List<InventoryItem>>
    fun getAllOrderedByAscending(): Flow<List<InventoryItem>>
    fun getAllOrderedByDescending(): Flow<List<InventoryItem>>
    fun getAllByCategory(category: String): Flow<List<InventoryItem>>
    fun getById(id: UUID): Flow<InventoryItem?>
    fun orderByAscending(category: String): Flow<List<InventoryItem>>
    fun orderByDescending(category: String): Flow<List<InventoryItem>>
    suspend fun add(inventoryItem: InventoryItem)
    suspend fun update(inventoryItem: InventoryItem)
    suspend fun delete(inventoryItem: InventoryItem)
    suspend fun deleteAll()
}