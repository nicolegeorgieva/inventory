package com.example.inventory.data.datasource.inventory

import com.example.inventory.data.database.dao.InventoryDao
import com.example.inventory.data.database.entity.InventoryEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class InventoryDataSource @Inject constructor(
    private val dao: InventoryDao
) {
    fun getAll(): Flow<List<InventoryEntity>> {
        return dao.getAll()
    }

    fun getAllOrderedByAscending(): Flow<List<InventoryEntity>> {
        return dao.getAllOrderedByAscending()
    }

    fun getAllOrderedByDescending(): Flow<List<InventoryEntity>> {
        return dao.getAllOrderedByDescending()
    }

    fun getAllByCategory(category: String): Flow<List<InventoryEntity>> {
        return dao.getAllByCategory(category)
    }

    fun getById(id: UUID): Flow<InventoryEntity?> {
        return dao.getById(id)
    }

    fun orderByAscending(category: String): Flow<List<InventoryEntity>> {
        return dao.orderByAscending(category)
    }

    fun orderByDescending(category: String): Flow<List<InventoryEntity>> {
        return dao.orderByDescending(category)
    }

    suspend fun save(inventoryEntity: InventoryEntity) {
        dao.save(inventoryEntity)
    }

    suspend fun delete(inventoryEntity: InventoryEntity) {
        dao.delete(inventoryEntity)
    }

    suspend fun deleteAll() {
        dao.deleteAll()
    }
}