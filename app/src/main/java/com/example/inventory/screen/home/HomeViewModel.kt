package com.example.inventory.screen.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import com.example.inventory.ComposeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ComposeViewModel<HomeState, HomeEvent>() {
    private val name = mutableStateOf<String?>(null)
    private val inventoryList = mutableStateOf<ImmutableList<InventoryUi>?>(null)

    @Composable
    override fun uiState(): HomeState {
        return HomeState(
            name = getName(),
            inventoryList = getInventoryList()
        )
    }

    @Composable
    private fun getName(): String? {
        return name.value
    }

    @Composable
    private fun getInventoryList(): ImmutableList<InventoryUi>? {
        return inventoryList.value
    }

    override fun onEvent(event: HomeEvent) {
        TODO("Not yet implemented")
    }
}