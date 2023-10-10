package com.example.inventory.data.repository

import com.example.inventory.data.datasource.InventoryDataSource
import com.example.inventory.data.model.InventoryItem
import com.example.inventory.data.repository.mapper.InventoryMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

class InventoryRepository @Inject constructor(
    private val inventoryDataSource: InventoryDataSource,
    private val inventoryMapper: InventoryMapper
) {
    suspend fun getAll(): List<InventoryItem> {
        return withContext(Dispatchers.IO) {
            inventoryDataSource.getAll().map {
                inventoryMapper.entityToDomain(it)
            }
        }
    }

    suspend fun getAllByCategory(category: String): List<InventoryItem> {
        return withContext(Dispatchers.IO) {
            inventoryDataSource.getAllByCategory(category).map {
                inventoryMapper.entityToDomain(it)
            }
        }
    }

    suspend fun getById(id: UUID): InventoryItem? {
        return withContext(Dispatchers.IO) {
            inventoryDataSource.getById(id)?.let {
                inventoryMapper.entityToDomain(it)
            }
        }
    }

    suspend fun orderByAscending(): List<InventoryItem> {
        return withContext(Dispatchers.IO) {
            inventoryDataSource.orderByAscending().map {
                inventoryMapper.entityToDomain(it)
            }
        }
    }

    suspend fun orderByDescending(): List<InventoryItem> {
        return withContext(Dispatchers.IO) {
            inventoryDataSource.orderByDescending().map {
                inventoryMapper.entityToDomain(it)
            }
        }
    }

    suspend fun save(inventoryItem: InventoryItem) {
        withContext(Dispatchers.IO) {
            inventoryDataSource.save(inventoryMapper.domainToEntity(inventoryItem))
        }
    }

    suspend fun delete(inventoryItem: InventoryItem) {
        withContext(Dispatchers.IO) {
            inventoryDataSource.delete(inventoryMapper.domainToEntity(inventoryItem))
        }
    }

    suspend fun deleteAll() {
        withContext(Dispatchers.IO) {
            inventoryDataSource.deleteAll()
        }
    }
}