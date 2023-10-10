package com.example.inventory.data.repository.mapper

import com.example.inventory.data.database.entity.InventoryEntity
import com.example.inventory.data.model.InventoryItem
import javax.inject.Inject

class InventoryMapper @Inject constructor() {
    fun entityToDomain(entity: InventoryEntity): InventoryItem {
        return InventoryItem(
            id = entity.id,
            name = entity.name,
            quantity = entity.quantity,
            imageUrl = entity.imageUrl,
            category = entity.category
        )
    }

    fun domainToEntity(item: InventoryItem): InventoryEntity {
        return InventoryEntity(
            id = item.id,
            name = item.name,
            quantity = item.quantity,
            imageUrl = item.imageUrl,
            category = item.category
        )
    }
}