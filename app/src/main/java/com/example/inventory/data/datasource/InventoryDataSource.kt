package com.example.inventory.data.datasource

import com.example.inventory.data.database.dao.InventoryDao
import com.example.inventory.data.database.entity.InventoryEntity
import java.util.UUID
import javax.inject.Inject

class InventoryDataSource @Inject constructor(
    private val dao: InventoryDao
) {
    suspend fun getAllOrderedByAscending(): List<InventoryEntity> {
        return dao.getAllOrderedByAscending()
    }

    suspend fun getAllOrderedByDescending(): List<InventoryEntity> {
        return dao.getAllOrderedByDescending()
    }

    suspend fun getAllByCategory(category: String): List<InventoryEntity> {
        return dao.getAllByCategory(category)
    }

    suspend fun getById(id: UUID): InventoryEntity? {
        return dao.getById(id)
    }

    suspend fun orderByAscending(category: String): List<InventoryEntity> {
        return dao.orderByAscending(category)
    }

    suspend fun orderByDescending(category: String): List<InventoryEntity> {
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