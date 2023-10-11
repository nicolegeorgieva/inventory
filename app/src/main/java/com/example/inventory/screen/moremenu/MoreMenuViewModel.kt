package com.example.inventory.screen.moremenu

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import com.example.inventory.ComposeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoreMenuViewModel @Inject constructor() : ComposeViewModel<MoreMenuState, MoreMenuEvent>() {
    private val name = mutableStateOf("")

    @Composable
    override fun uiState(): MoreMenuState {
        return MoreMenuState(
            name = getName()
        )
    }

    @Composable
    private fun getName(): String {
        return name.value
    }

    override fun onEvent(event: MoreMenuEvent) {
        when (event) {
            is MoreMenuEvent.ChangeName -> setName(event.newName)
        }
    }

    private fun setName(newName: String) {
        name.value = newName
    }
}