package com.example.inventory

import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Navigator @Inject constructor() {
    val navigationEvents = MutableSharedFlow<String>()

    suspend fun navigate(route: String) {
        navigationEvents.emit(route)
    }
}