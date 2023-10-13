package com.example.inventory.data.model

import java.util.UUID

data class InventoryItem(
    val id: UUID,
    val name: String,
    val quantity: Int,
    val minQuantityTarget: Int,
    val category: String?,
    val description: String?,
    val imagePath: String?
)