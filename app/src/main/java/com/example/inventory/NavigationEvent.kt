package com.example.inventory

sealed interface NavigationEvent {
    data class Route(val route: String) : NavigationEvent
    data object Back : NavigationEvent
}