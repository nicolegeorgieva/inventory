package com.example.inventory.data.repository.inventory

import com.example.inventory.DispatcherProvider
import com.example.inventory.data.datasource.InventoryDataSource
import com.example.inventory.data.model.InventoryItem
import com.example.inventory.data.repository.mapper.InventoryMapper
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

class InventoryRepositoryImpl @Inject constructor(
    private val dataSource: InventoryDataSource,
    private val mapper: InventoryMapper,
    private val dispatchers: DispatcherProvider
) : InventoryRepository {
    override suspend fun getAll(): List<InventoryItem> {
        return withContext(dispatchers.io) {
            dataSource.getAll().map {
                mapper.entityToDomain(it)
            }
        }
    }

    override suspend fun getAllByCategory(category: String): List<InventoryItem> {
        return withContext(dispatchers.io) {
            dataSource.getAllByCategory(category).map {
                mapper.entityToDomain(it)
            }
        }
    }

    override suspend fun getById(id: UUID): InventoryItem? {
        return withContext(dispatchers.io) {
            dataSource.getById(id)?.let {
                mapper.entityToDomain(it)
            }
        }
    }

    override suspend fun orderByAscending(): List<InventoryItem> {
        return withContext(dispatchers.io) {
            dataSource.orderByAscending().map {
                mapper.entityToDomain(it)
            }
        }
    }

    override suspend fun orderByDescending(): List<InventoryItem> {
        return withContext(dispatchers.io) {
            dataSource.orderByDescending().map {
                mapper.entityToDomain(it)
            }
        }
    }

    override suspend fun add(inventoryItem: InventoryItem) {
        val checkExistingName = getAll().filter { it.name == inventoryItem.name }
        if (checkExistingName.isNotEmpty()) return

        withContext(dispatchers.io) {
            dataSource.save(mapper.domainToEntity(inventoryItem))
        }
    }

    override suspend fun update(inventoryItem: InventoryItem) {
        if (dataSource.getById(inventoryItem.id) == null) return

        withContext(dispatchers.io) {
            dataSource.save(mapper.domainToEntity(inventoryItem))
        }
    }

    override suspend fun delete(inventoryItem: InventoryItem) {
        withContext(dispatchers.io) {
            dataSource.delete(mapper.domainToEntity(inventoryItem))
        }
    }

    override suspend fun deleteAll() {
        withContext(dispatchers.io) {
            dataSource.deleteAll()
        }
    }
}