package com.example.inventory.data.database

import androidx.room.TypeConverter
import java.util.UUID

class RoomTypeConverters {
    @TypeConverter
    fun uuidToString(id: UUID): String {
        return id.toString()
    }

    @TypeConverter
    fun stringToUuid(id: String): UUID {
        return UUID.fromString(id)
    }
}