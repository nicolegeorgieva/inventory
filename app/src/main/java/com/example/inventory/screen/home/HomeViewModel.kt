package com.example.inventory.screen.home

import androidx.compose.runtime.Composable
import com.example.inventory.ComposeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(

) : ComposeViewModel<HomeState, HomeEvent>() {
    @Composable
    override fun uiState(): HomeState {
        TODO("Not yet implemented")
    }

    override fun onEvent(event: HomeEvent) {
        TODO("Not yet implemented")
    }
}