package com.example.inventory.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.inventory.data.database.entity.InventoryEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface InventoryDao {
    @Query("SELECT * FROM inventory_items")
    fun getAll(): Flow<List<InventoryEntity>>

    @Query("SELECT * FROM inventory_items ORDER BY (quantity - min_quantity_target) ASC")
    fun getAllOrderedByAscending(): Flow<List<InventoryEntity>>

    @Query("SELECT * FROM inventory_items ORDER BY (quantity - min_quantity_target) DESC")
    fun getAllOrderedByDescending(): Flow<List<InventoryEntity>>

    @Query("SELECT * FROM inventory_items WHERE category = :category")
    fun getAllByCategory(category: String): Flow<List<InventoryEntity>>

    @Query("SELECT * FROM inventory_items WHERE id = :id")
    fun getById(id: UUID): Flow<InventoryEntity?>

    @Query(
        "SELECT * FROM inventory_items WHERE category = :category ORDER BY " +
                "(quantity - min_quantity_target) ASC"
    )
    fun orderByAscending(category: String): Flow<List<InventoryEntity>>

    @Query(
        "SELECT * FROM inventory_items WHERE category = :category ORDER BY " +
                "(quantity - min_quantity_target) DESC"
    )
    fun orderByDescending(category: String): Flow<List<InventoryEntity>>

    @Upsert
    suspend fun save(inventoryEntity: InventoryEntity)

    @Delete
    suspend fun delete(inventoryEntity: InventoryEntity)

    @Query("DELETE FROM inventory_items")
    suspend fun deleteAll()
}