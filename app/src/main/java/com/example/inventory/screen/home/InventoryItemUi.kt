package com.example.inventory.screen.home

import androidx.compose.runtime.Immutable

@Immutable
data class InventoryItemUi(
    val id: String,
    val name: String,
    val quantity: String,
    val imagePath: String?,
    val category: String?
)