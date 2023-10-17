package com.example.inventory.data.repository

import com.example.inventory.data.datasource.InventoryDataSource
import com.example.inventory.data.model.InventoryItem
import com.example.inventory.data.repository.mapper.InventoryMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

class InventoryRepository @Inject constructor(
    private val dataSource: InventoryDataSource,
    private val mapper: InventoryMapper
) {
    suspend fun getAll(): List<InventoryItem> {
        return withContext(Dispatchers.IO) {
            dataSource.getAll().map {
                mapper.entityToDomain(it)
            }
        }
    }

    suspend fun getAllByCategory(category: String): List<InventoryItem> {
        return withContext(Dispatchers.IO) {
            dataSource.getAllByCategory(category).map {
                mapper.entityToDomain(it)
            }
        }
    }

    suspend fun getById(id: UUID): InventoryItem? {
        return withContext(Dispatchers.IO) {
            dataSource.getById(id)?.let {
                mapper.entityToDomain(it)
            }
        }
    }

    suspend fun orderByAscending(): List<InventoryItem> {
        return withContext(Dispatchers.IO) {
            dataSource.orderByAscending().map {
                mapper.entityToDomain(it)
            }
        }
    }

    suspend fun orderByDescending(): List<InventoryItem> {
        return withContext(Dispatchers.IO) {
            dataSource.orderByDescending().map {
                mapper.entityToDomain(it)
            }
        }
    }

    suspend fun add(inventoryItem: InventoryItem) {
        val checkExistingName = getAll().filter { it.name == inventoryItem.name }
        if (checkExistingName.isNotEmpty()) return

        withContext(Dispatchers.IO) {
            dataSource.save(mapper.domainToEntity(inventoryItem))
        }
    }

    suspend fun update(inventoryItem: InventoryItem) {
        if (dataSource.getById(inventoryItem.id) == null) return

        withContext(Dispatchers.IO) {
            dataSource.save(mapper.domainToEntity(inventoryItem))
        }
    }

    suspend fun delete(inventoryItem: InventoryItem) {
        withContext(Dispatchers.IO) {
            dataSource.delete(mapper.domainToEntity(inventoryItem))
        }
    }

    suspend fun deleteAll() {
        withContext(Dispatchers.IO) {
            dataSource.deleteAll()
        }
    }
}