package com.example.inventory.screen.home

import com.example.inventory.data.model.InventoryItem
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import javax.inject.Inject

class InventoryListProvider @Inject constructor() {
    fun generateInventoryList(
        items: List<InventoryItem>,
        sortByAscending: Boolean,
    ): ImmutableList<InventoryItemType> {
        val toBuy = provideItemsBySection(
            items, SectionType.TOBUY
        ).map {
            InventoryItemType.Item(
                item = it
            )
        }

        val enough = provideItemsBySection(
            items, SectionType.ENOUGH
        ).map {
            InventoryItemType.Item(
                item = it
            )
        }

        if (toBuy.isEmpty() && enough.isEmpty()) {
            return persistentListOf<InventoryItemType.Item>()
        }

        return provideSortedItemsList(sortByAscending, toBuy, enough)
    }

    private fun provideItemsBySection(
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

        return itemsBySection.map {
            InventoryItemUi(
                id = it.id.toString(),
                name = it.name,
                quantity = it.quantity.toString(),
                imagePath = it.imagePath,
                category = it.category
            )
        }.toImmutableList()
    }

    private fun provideSortedItemsList(
        sortByAscending: Boolean,
        toBuy: List<InventoryItemType.Item>,
        enough: List<InventoryItemType.Item>
    ): ImmutableList<InventoryItemType> {
        val toBuySection = buildList {
            add(InventoryItemType.Section(SectionType.TOBUY, toBuy.size))
            addAll(toBuy)
        }

        val enoughSection = buildList {
            add(InventoryItemType.Section(SectionType.ENOUGH, enough.size))
            addAll(enough)
        }

        return if (sortByAscending) {
            buildList {
                addAll(toBuySection)
                addAll(enoughSection)
            }.toImmutableList()
        } else {
            buildList {
                addAll(enoughSection)
                addAll(toBuySection)
            }.toImmutableList()
        }
    }
}