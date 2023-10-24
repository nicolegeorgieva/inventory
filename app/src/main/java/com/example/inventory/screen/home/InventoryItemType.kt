package com.example.inventory.screen.home

sealed interface InventoryItemType {
    data class Section(val section: SectionType, val count: Int) : InventoryItemType
    data class Item(val item: InventoryItemUi) : InventoryItemType
}