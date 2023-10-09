package com.example.inventory

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel

/*
Should be used in the ViewModels of the screens.
It's a must to override the uiState and onEvent fun - return uiState and handle events.
 */
abstract class ComposeViewModel<S, E> : ViewModel() {
    @Composable
    abstract fun uiState(): S

    abstract fun onEvent(event: E)
}