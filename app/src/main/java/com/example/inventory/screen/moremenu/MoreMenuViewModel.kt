package com.example.inventory.screen.moremenu

import androidx.compose.runtime.Composable
import com.example.inventory.ComposeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoreMenuViewModel @Inject constructor() : ComposeViewModel<MoreMenuState, MoreMenuEvent>() {
    @Composable
    override fun uiState(): MoreMenuState {
        return MoreMenuState("")
    }

    override fun onEvent(event: MoreMenuEvent) {}
}