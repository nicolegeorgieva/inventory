package com.example.inventory.screen.home

sealed interface HomeEvent {
    data class IncreaseQuantity(val id: String) : HomeEvent
    data class DecreaseQuantity(val id: String) : HomeEvent
}