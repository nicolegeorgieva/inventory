package com.example.inventory.screen.addinventoryitem

import androidx.compose.runtime.Composable
import com.example.inventory.ComposeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddInventoryItemViewModel @Inject constructor() :
    ComposeViewModel<AddInventoryItemState, AddInventoryItemEvent>() {
    @Composable
    override fun uiState(): AddInventoryItemState {
        TODO("Not yet implemented")
    }

    override fun onEvent(event: AddInventoryItemEvent) {
        TODO("Not yet implemented")
    }
}