package com.example.inventory.screen.home

sealed interface InventoryType {
    data class Item(val item: InventoryItemUi) : InventoryType
    data class SectionDivider(val title: String) : InventoryType
}