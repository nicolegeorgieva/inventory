package com.example.inventory.screen.addinventoryitem

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun AddInventoryItemScreen(navController: NavController) {
    val viewModel: AddInventoryItemViewModel = hiltViewModel()
    val uiState = viewModel.uiState()
}

@Composable
private fun AddInventoryItemUi(
    navController: NavController?,
    uiState: AddInventoryItemState,
    onEvent: (AddInventoryItemEvent) -> Unit
) {

}

@Preview
@Composable
private fun EmptyStatePreview() {
    AddInventoryItemUi(
        navController = null,
        uiState = AddInventoryItemState(),
        onEvent = {}
    )
}