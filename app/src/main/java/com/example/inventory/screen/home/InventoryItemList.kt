package com.example.inventory.screen.home

import kotlinx.collections.immutable.ImmutableList
import javax.annotation.concurrent.Immutable

@Immutable
data class InventoryItemList(
    val toBuySection: SectionType = SectionType.TOBUY,
    val toBuyItems: ImmutableList<InventoryItemUi>?,
    val enoughSection: SectionType = SectionType.ENOUGH,
    val enoughItems: ImmutableList<InventoryItemUi>?
)