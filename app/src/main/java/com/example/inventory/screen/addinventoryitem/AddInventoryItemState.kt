package com.example.inventory.screen.addinventoryitem

data class AddInventoryItemState(
    val name: String?,
    val quantity: Int?,
    val minQuantityTarget: Int?,
    val category: String?,
    val description: String?,
    val image: String?
)