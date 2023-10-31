package com.example.inventory.screen.addoreditinventoryitem.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.inventory.R
import com.example.inventory.ui.theme.InventoryTheme

@Composable
fun DeleteItemButton(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = stringResource(R.string.delete)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DeleteItemButtonPreview() {
    InventoryTheme {
        DeleteItemButton {}
    }
}