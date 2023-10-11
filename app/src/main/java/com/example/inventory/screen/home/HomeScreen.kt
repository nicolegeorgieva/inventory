package com.example.inventory.screen.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.inventory.ui.theme.InventoryTheme
import kotlinx.collections.immutable.persistentListOf

@Composable
fun HomeScreen() {
    val viewModel: HomeViewModel = hiltViewModel()
    val uiState = viewModel.uiState()
}

@Composable
fun HomeUi(
    uiState: HomeState,
    onEvent: (HomeEvent) -> Unit
) {

}

@Preview(showBackground = true)
@Composable
fun HomeUiPreview() {
    InventoryTheme {
        HomeUi(
            uiState = HomeState(inventoryList = persistentListOf()),
            onEvent = {}
        )
    }
}