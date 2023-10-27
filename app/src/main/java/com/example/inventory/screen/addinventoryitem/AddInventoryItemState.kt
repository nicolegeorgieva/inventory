package com.example.inventory.screen.addinventoryitem

import kotlinx.collections.immutable.ImmutableList

data class AddInventoryItemState(
    val name: String?,
    val quantity: String?,
    val minQuantityTarget: String?,
    val category: String,
    val categories: ImmutableList<String>,
    val expanded: Boolean,
    val openAddCategoryDialog: Boolean,
    val newCategoryValue: String?,
    val description: String?,
    val imagePath: String?,
    val link: String?,
    val addWithoutRequired: Boolean
)