package com.example.inventory.screen.addoreditinventoryitem.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.inventory.R
import com.example.inventory.screen.addoreditinventoryitem.AddEditInventoryItemEvent

@Composable
fun AddUpdateInventoryItemButton(
    itemId: String?,
    onEvent: (AddEditInventoryItemEvent) -> Unit
) {
    FloatingActionButton(
        onClick = {
            onEvent(AddEditInventoryItemEvent.AddInventoryItem)
        },
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        if (itemId == null) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(R.string.add)
            )
        } else {
            Text(text = stringResource(R.string.save))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AddInventoryItemButtonPreview() {
    AddUpdateInventoryItemButton(
        itemId = null,
        onEvent = {}
    )
}