package com.example.inventory.screen.home

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList

@Immutable
data class HomeState(
    val name: String?,
    val quote: String?,
    val sortByAscending: Boolean,
    val categoryFilter: String,
    val categories: ImmutableList<String>,
    val categoryFilterMenuExpanded: Boolean,
    val inventoryItemList: ImmutableList<InventoryItemType>?
)