package com.example.inventory

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/*
Should be used in the ViewModels of the screens.
It's a must to override the uiState and onEvent fun - return uiState and handle events.
 */
@HiltViewModel
abstract class ComposeViewModel<S, E> @Inject constructor() : ViewModel() {
    @Composable
    abstract fun uiState(): S

    abstract fun onEvent(event: E)
}