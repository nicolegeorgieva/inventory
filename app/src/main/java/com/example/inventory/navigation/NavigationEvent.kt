package com.example.inventory.navigation

sealed interface NavigationEvent {
    data class Route(val route: String) : NavigationEvent
    data object Back : NavigationEvent
}