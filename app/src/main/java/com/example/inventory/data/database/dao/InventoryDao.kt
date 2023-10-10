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
    fun getAll(): List<InventoryEntity>

    @Query("SELECT * FROM inventory_items WHERE id = :id")
    fun getById(id: UUID): InventoryEntity?

    @Query("SELECT * FROM inventory_items ORDER BY quantity ASC")
    fun orderByAscending(): List<InventoryEntity>

    @Query("SELECT * FROM inventory_items ORDER BY quantity DESC")
    fun orderByDescending(): List<InventoryEntity>

    @Upsert
    fun save(inventoryEntity: InventoryEntity)

    @Delete
    fun delete(inventoryEntity: InventoryEntity)

    @Query("DELETE FROM inventory_items")
    fun deleteAll()
}