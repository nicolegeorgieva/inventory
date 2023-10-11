package com.example.inventory.screen.moremenu

sealed interface MoreMenuEvent {
    data class ChangeName(val newName: String) : MoreMenuEvent
}