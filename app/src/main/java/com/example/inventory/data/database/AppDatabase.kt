package com.example.inventory.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.inventory.data.database.dao.InventoryDao
import com.example.inventory.data.database.entity.InventoryEntity

@Database(
    entities = [
        InventoryEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun inventoryDao(): InventoryDao
}