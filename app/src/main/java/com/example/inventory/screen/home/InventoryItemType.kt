package com.example.inventory.screen.home

sealed interface InventoryItemType {
    data class Section(val section: SectionType) : InventoryItemType
    data class Item(val item: InventoryItemUi) : InventoryItemType
}