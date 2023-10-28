package com.example.inventory.screen.addoreditinventoryitem.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun DeleteButton(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Delete"
        )
    }
}

@Composable
fun DeleteItemConfirmationDialog(
    onConfirmDeleting: () -> Unit,
    onCloseDeleteItemDialog: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onCloseDeleteItemDialog,
        icon = {
            Icon(imageVector = Icons.Filled.Warning, contentDescription = "Warning")
        },
        title = {
            Text(text = "Delete item?")
        },
        text = {
            Text(
                text = "Deleting this item will remove it permanently from your inventory. " +
                        "Are you sure you want to delete it?"
            )
        },
        confirmButton = {
            TextButton(
                onClick = onConfirmDeleting
            ) {
                Text(text = "Delete", color = MaterialTheme.colorScheme.error)
            }
        },
        dismissButton = {
            TextButton(
                onClick = onCloseDeleteItemDialog
            ) {
                Text(text = "Cancel")
            }
        }
    )
}