package com.example.inventory.screen.home

sealed interface HomeEvent {
    data class OnSortOptionClicked(val sortByAscending: Boolean) : HomeEvent
    data class OnCategoryFilterMenuExpandedChange(val expanded: Boolean) : HomeEvent
    data class OnCategoryFilterOptionSelected(val option: String) : HomeEvent
    data class IncreaseQuantity(val id: String) : HomeEvent
    data class DecreaseQuantity(val id: String) : HomeEvent
    data object OnAddButtonClicked : HomeEvent
}