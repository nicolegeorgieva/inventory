package com.example.inventory.screen.moremenu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.inventory.R
import com.example.inventory.component.CustomTopAppBar
import com.example.inventory.screen.moremenu.component.NameRow
import com.example.inventory.ui.theme.InventoryTheme

@Composable
fun MoreMenuScreen(navController: NavController) {
    val viewModel: MoreMenuViewModel = hiltViewModel()
    val uiState = viewModel.uiState()

    MoreMenuUi(
        navController = navController,
        uiState = uiState,
        onEvent = viewModel::onEvent
    )
}

@Composable
private fun MoreMenuUi(
    navController: NavController?,
    uiState: MoreMenuState,
    onEvent: (MoreMenuEvent) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CustomTopAppBar(
                title = stringResource(R.string.more_menu_settings_title),
                navController = navController
            )
        },
        content = { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = innerPadding
            ) {
                item("name input row") {
                    NameRow(
                        nameValue = uiState.name ?: "",
                        onNameChange = {
                            onEvent(MoreMenuEvent.ChangeName(it))
                        }
                    )
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun MoreMenuNullNamePreview() {
    InventoryTheme {
        MoreMenuUi(
            navController = null,
            uiState = MoreMenuState(name = null),
            onEvent = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MoreMenuExistingNamePreview() {
    InventoryTheme {
        MoreMenuUi(
            navController = null,
            uiState = MoreMenuState(name = "Amy"),
            onEvent = {}
        )
    }
}