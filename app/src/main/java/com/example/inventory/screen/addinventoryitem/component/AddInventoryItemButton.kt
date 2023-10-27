package com.example.inventory.screen.addinventoryitem.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.inventory.R
import com.example.inventory.screen.addinventoryitem.AddInventoryItemEvent

@Composable
fun AddInventoryItemButton(
    onEvent: (AddInventoryItemEvent) -> Unit
) {
    FloatingActionButton(
        onClick = {
            onEvent(AddInventoryItemEvent.AddInventoryItem)
        },
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(R.string.add)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AddInventoryItemButtonPreview() {
    AddInventoryItemButton(onEvent = {})
}