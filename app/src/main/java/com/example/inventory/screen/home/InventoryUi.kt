package com.example.inventory.screen.home

import androidx.compose.runtime.Immutable

@Immutable
data class InventoryUi(
    val id: String,
    val name: String,
    val quantity: Int,
    val imageUrl: String?,
    val category: String?
)