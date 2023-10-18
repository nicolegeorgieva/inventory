package com.example.inventory.screen.moremenu

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.inventory.ComposeViewModel
import com.example.inventory.data.repository.name.NameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoreMenuViewModel @Inject constructor(
    private val nameRepository: NameRepository
) : ComposeViewModel<MoreMenuState, MoreMenuEvent>() {
    private val name = mutableStateOf<String?>(null)

    @Composable
    override fun uiState(): MoreMenuState {
        LaunchedEffect(Unit) {
            name.value = nameRepository.getName()
        }

        return MoreMenuState(
            name = getName()
        )
    }

    @Composable
    private fun getName(): String? {
        return name.value
    }

    override fun onEvent(event: MoreMenuEvent) {
        when (event) {
            is MoreMenuEvent.ChangeName -> setName(event.newName)
        }
    }

    private fun setName(newName: String) {
        if (newName.isBlank()) {
            name.value = null

            viewModelScope.launch {
                nameRepository.removeName()
            }
        } else {
            name.value = newName

            viewModelScope.launch {
                nameRepository.setName(newName)
            }
        }
    }
}