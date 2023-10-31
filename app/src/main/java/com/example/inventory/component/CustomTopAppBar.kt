package com.example.inventory.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.inventory.screen.addoreditinventoryitem.component.DeleteItemButton
import com.example.inventory.ui.theme.InventoryTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    navController: NavController?,
    title: String,
    action: (@Composable () -> Unit)? = null
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = title,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            BackButton {
                navController?.popBackStack()
            }
        },
        actions = {
            if (action != null) {
                action()
            }
        }
    )
}

@Preview
@Composable
private fun CustomTopAppBarPreview() {
    InventoryTheme {
        CustomTopAppBar(
            navController = null,
            title = "Settings"
        )
    }
}

@Preview
@Composable
private fun CustomTopAppBarEditItemPreview() {
    InventoryTheme {
        CustomTopAppBar(
            navController = null,
            title = "Settings",
            action = {
                DeleteItemButton(onClick = {})
            }
        )
    }
}