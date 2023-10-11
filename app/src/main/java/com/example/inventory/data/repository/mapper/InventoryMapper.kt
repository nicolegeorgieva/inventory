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
            minQuantityTarget = entity.minQuantityTarget,
            category = entity.category,
            description = entity.description,
            imageUrl = entity.imageUrl,
        )
    }

    fun domainToEntity(item: InventoryItem): InventoryEntity {
        return InventoryEntity(
            id = item.id,
            name = item.name,
            quantity = item.quantity,
            minQuantityTarget = item.minQuantityTarget,
            category = item.category,
            description = item.description,
            imageUrl = item.imageUrl
        )
    }
}