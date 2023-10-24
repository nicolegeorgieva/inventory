package com.example.inventory.screen.home

import kotlinx.collections.immutable.ImmutableList

sealed interface InventoryType {
    data class Items(val items: ImmutableList<InventoryItemUi>) : InventoryType
    data class SectionDivider(val section: SectionType) : InventoryType
}