package com.example.inventory.screen.home

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList

@Immutable
data class HomeState(
    val name: String?,
    val sortByAscending: Boolean,
    val categoryFilter: String,
    val categoryFilterMenuExpanded: Boolean,
    val inventoryItemList: ImmutableList<InventoryItemType>?
)