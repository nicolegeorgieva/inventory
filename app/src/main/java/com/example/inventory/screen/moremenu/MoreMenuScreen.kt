package com.example.inventory.screen.moremenu

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.inventory.R
import com.example.inventory.component.BackButton
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
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        item {
            BackButton {
                navController?.popBackStack()
            }
        }

        item {
            NameRow(
                nameValue = uiState.name,
                onNameChange = {
                    onEvent(MoreMenuEvent.ChangeName(it))
                }
            )
        }
    }
}

@Composable
private fun NameRow(
    nameValue: String,
    onNameChange: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = stringResource(R.string.more_menu_name_label))

        Spacer(modifier = Modifier.width(8.dp))

        TextField(
            modifier = Modifier.width(124.dp),
            value = nameValue,
            onValueChange = onNameChange
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MoreMenuPreview() {
    InventoryTheme {
        MoreMenuUi(
            navController = null,
            uiState = MoreMenuState(name = "Amy"),
            onEvent = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MoreMenuPreview2() {
    InventoryTheme {
        MoreMenuUi(
            navController = null,
            uiState = MoreMenuState(name = ""),
            onEvent = {}
        )
    }
}