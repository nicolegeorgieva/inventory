package com.example.inventory.screen.home

import kotlinx.collections.immutable.ImmutableList

data class HomeState(
    val inventoryList: ImmutableList<String>
)