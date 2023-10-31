package com.example.inventory.screen.addoreditinventoryitem.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.inventory.R
import com.example.inventory.ui.theme.InventoryTheme

@Composable
fun DeleteItemConfirmationDialog(
    onConfirmDeleting: () -> Unit,
    onCloseDeleteItemDialog: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onCloseDeleteItemDialog,
        icon = {
            Icon(
                imageVector = Icons.Filled.Warning,
                contentDescription = stringResource(R.string.warning)
            )
        },
        title = {
            Text(text = "Delete item?")
        },
        text = {
            Text(text = stringResource(R.string.delete_item_warning_message))
        },
        confirmButton = {
            TextButton(
                onClick = onConfirmDeleting
            ) {
                Text(
                    text = stringResource(R.string.delete),
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onCloseDeleteItemDialog
            ) {
                Text(text = stringResource(R.string.cancel))
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun DeleteItemConfirmationDialogPreview() {
    InventoryTheme {
        DeleteItemConfirmationDialog(
            onConfirmDeleting = {},
            onCloseDeleteItemDialog = {}
        )
    }
}