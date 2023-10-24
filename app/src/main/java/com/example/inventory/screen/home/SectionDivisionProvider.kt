package com.example.inventory.screen.home

import com.example.inventory.data.model.InventoryItem
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import javax.inject.Inject

class SectionDivisionProvider @Inject constructor() {
    fun provideItemsBySection(
        items: List<InventoryItem>,
        section: SectionType
    ): ImmutableList<InventoryItemUi> {

        val itemsBySection = when (section) {
            SectionType.TOBUY -> items.filter {
                it.quantity < it.minQuantityTarget
            }

            SectionType.ENOUGH -> items.filter {
                it.quantity >= it.minQuantityTarget
            }
        }

        return if (itemsBySection.isNotEmpty()) {
            itemsBySection.map {
                InventoryItemUi(
                    id = it.id.toString(),
                    name = it.name,
                    quantity = it.quantity.toString(),
                    imagePath = it.imagePath,
                    category = it.category
                )
            }.toImmutableList()
        } else {
            persistentListOf()
        }
    }
}