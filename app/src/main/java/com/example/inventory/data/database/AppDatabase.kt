package com.example.inventory.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.inventory.data.database.dao.InventoryDao
import com.example.inventory.data.database.entity.InventoryEntity

@Database(
    entities = [
        InventoryEntity::class
    ],
    version = 1
)
@TypeConverters(RoomTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun inventoryDao(): InventoryDao
}