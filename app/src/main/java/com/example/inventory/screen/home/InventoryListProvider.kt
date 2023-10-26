package com.example.inventory.screen.home

import androidx.compose.runtime.MutableState
import com.example.inventory.data.model.InventoryItem
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import javax.inject.Inject

class InventoryListProvider @Inject constructor() {
    fun generateInventoryList(
        items: List<InventoryItem>,
        sortByAscending: MutableState<Boolean>,
        inventoryItemList: MutableState<ImmutableList<InventoryItemType>?>
    ) {
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

        inventoryItemList.value = if (sortByAscending.value) {
            buildList {
                add(InventoryItemType.Section(SectionType.TOBUY, toBuy.size))
                addAll(toBuy)
                add(InventoryItemType.Section(SectionType.ENOUGH, enough.size))
                addAll(enough)
            }.toImmutableList()
        } else {
            buildList {
                add(InventoryItemType.Section(SectionType.ENOUGH, enough.size))
                addAll(enough)
                add(InventoryItemType.Section(SectionType.TOBUY, toBuy.size))
                addAll(toBuy)
            }.toImmutableList()
        }
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
}