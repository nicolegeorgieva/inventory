package com.example.inventory.screen.home

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList

@Immutable
data class HomeState(
    val name: String?,
    val inventoryList: ImmutableList<InventoryUi>?
)