package com.example.inventory.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.inventory.data.database.entity.InventoryEntity
import java.util.UUID

@Dao
interface InventoryDao {
    @Query("SELECT * FROM inventory_items")
    suspend fun getAll(): List<InventoryEntity>

    @Query("SELECT * FROM inventory_items WHERE category = :category")
    suspend fun getAllByCategory(category: String): List<InventoryEntity>

    @Query("SELECT * FROM inventory_items WHERE id = :id")
    suspend fun getById(id: UUID): InventoryEntity?

    @Query("SELECT * FROM inventory_items ORDER BY quantity ASC")
    suspend fun orderByAscending(): List<InventoryEntity>

    @Query("SELECT * FROM inventory_items ORDER BY quantity DESC")
    suspend fun orderByDescending(): List<InventoryEntity>

    @Upsert
    suspend fun save(inventoryEntity: InventoryEntity)

    @Delete
    suspend fun delete(inventoryEntity: InventoryEntity)

    @Query("DELETE FROM inventory_items")
    suspend fun deleteAll()
}