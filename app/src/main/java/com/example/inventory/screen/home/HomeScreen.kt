package com.example.inventory.screen.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.inventory.ui.theme.InventoryTheme

@Composable
fun HomeScreen() {
    val viewModel: HomeViewModel = hiltViewModel()
}

@Composable
fun HomeUi() {

}

@Preview(showBackground = true)
@Composable
fun HomeUiPreview() {
    InventoryTheme {
        HomeUi()
    }
}