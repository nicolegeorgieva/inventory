package com.example.inventory.navigation

import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Navigator @Inject constructor() {
    val navigationEvents = MutableSharedFlow<NavigationEvent>()

    suspend fun navigate(route: String) {
        navigationEvents.emit(NavigationEvent.Route(route))
    }

    suspend fun back() {
        navigationEvents.emit(NavigationEvent.Back)
    }
}