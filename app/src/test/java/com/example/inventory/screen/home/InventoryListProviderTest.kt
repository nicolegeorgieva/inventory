package com.example.inventory.screen.home

import com.example.inventory.data.model.InventoryItem
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import kotlinx.collections.immutable.persistentListOf
import java.util.UUID

class InventoryListProviderTest : FreeSpec({
    "generate inventory list" - {
        "with items" {
            // given
            val inventoryListProvider = InventoryListProvider()
            val id = UUID.randomUUID()
            val id2 = UUID.randomUUID()
            val items = listOf(
                InventoryItem(
                    id = id,
                    name = "Item",
                    quantity = 6,
                    minQuantityTarget = 5,
                    category = null,
                    description = null,
                    imagePath = null
                ),
                InventoryItem(
                    id = id2,
                    name = "Item2",
                    quantity = 5,
                    minQuantityTarget = 5,
                    category = null,
                    description = null,
                    imagePath = null
                )
            )

            // when
            val list = inventoryListProvider.generateInventoryList(
                items = items,
                sortByAscending = false
            )

            // then
            list shouldBe persistentListOf(
                InventoryItemType.Section(SectionType.ENOUGH, 2),
                InventoryItemType.Item(
                    InventoryItemUi(
                        id = id.toString(),
                        name = "Item",
                        quantity = "6",
                        imagePath = null,
                        category = null
                    )
                ),
                InventoryItemType.Item(
                    InventoryItemUi(
                        id = id2.toString(),
                        name = "Item2",
                        quantity = "5",
                        imagePath = null,
                        category = null
                    )
                ),
                InventoryItemType.Section(SectionType.TOBUY, 0)
            )
        }
    }
})