package com.example.inventory.screen.home

sealed interface HomeEvent {
    data class AddQuantity(val id: String) : HomeEvent
    data class RemoveQuantity(val id: String) : HomeEvent
}