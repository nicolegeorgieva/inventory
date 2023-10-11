package com.example.inventory.screen.addinventoryitem

data class AddInventoryItemState(
    val name: String?,
    val quantity: String?,
    val minQuantityTarget: String?,
    val category: String?,
    val description: String?,
    val image: String?
)