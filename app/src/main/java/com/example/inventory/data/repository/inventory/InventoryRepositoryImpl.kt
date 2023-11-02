package com.example.inventory.data.repository.inventory

import com.example.inventory.data.datasource.inventory.InventoryDataSource
import com.example.inventory.data.model.InventoryItem
import com.example.inventory.data.repository.mapper.InventoryMapper
import com.example.inventory.dispatcher.DispatcherProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

class InventoryRepositoryImpl @Inject constructor(
    private val dataSource: InventoryDataSource,
    private val mapper: InventoryMapper,
    private val dispatchers: DispatcherProvider
) : InventoryRepository {
    override fun getAll(): Flow<List<InventoryItem>> {
        return dataSource.getAll().map {
            it.map { entity ->
                mapper.entityToDomain(entity)
            }
        }.flowOn(dispatchers.io)
    }

    override fun getAllOrderedByAscending(): Flow<List<InventoryItem>> {
        return dataSource.getAllOrderedByAscending().map {
            it.map { entity ->
                mapper.entityToDomain(entity)
            }
        }.flowOn(dispatchers.io)
    }

    override fun getAllOrderedByDescending(): Flow<List<InventoryItem>> {
        return dataSource.getAllOrderedByDescending().map {
            it.map { entity ->
                mapper.entityToDomain(entity)
            }
        }.flowOn(dispatchers.io)
    }

    override fun getAllByCategory(category: String): Flow<List<InventoryItem>> {
        return dataSource.getAllByCategory(category).map {
            it.map { entity ->
                mapper.entityToDomain(entity)
            }
        }.flowOn(dispatchers.io)
    }

    override fun getById(id: UUID): Flow<InventoryItem?> {
        return dataSource.getById(id).map {
            if (it != null) {
                mapper.entityToDomain(it)
            } else {
                null
            }
        }
    }

    override fun orderByAscending(category: String): Flow<List<InventoryItem>> {
        return dataSource.orderByAscending(category).map {
            it.map { entity ->
                mapper.entityToDomain(entity)
            }
        }.flowOn(dispatchers.io)
    }

    override fun orderByDescending(category: String): Flow<List<InventoryItem>> {
        return dataSource.orderByDescending(category).map {
            it.map { entity ->
                mapper.entityToDomain(entity)
            }
        }.flowOn(dispatchers.io)
    }

    override suspend fun add(inventoryItem: InventoryItem) {
        val checkExistingName = getAll().first().filter { it.name == inventoryItem.name }
        if (checkExistingName.isNotEmpty()) return

        withContext(dispatchers.io) {
            dataSource.save(mapper.domainToEntity(inventoryItem))
        }
    }

    override suspend fun update(inventoryItem: InventoryItem) {
        if (dataSource.getById(inventoryItem.id).first() == null) return

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