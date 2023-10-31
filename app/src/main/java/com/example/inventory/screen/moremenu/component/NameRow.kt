package com.example.inventory.screen.moremenu.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.inventory.R
import com.example.inventory.ui.theme.InventoryTheme

@Composable
fun NameRow(
    nameValue: String,
    onNameChange: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = stringResource(R.string.name_label))

        Spacer(modifier = Modifier.width(8.dp))

        OutlinedTextField(
            modifier = Modifier.width(124.dp),
            value = nameValue,
            onValueChange = onNameChange
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BlankNameRowPreview() {
    InventoryTheme {
        NameRow(nameValue = "", onNameChange = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun FilledNameRowPreview() {
    InventoryTheme {
        NameRow(nameValue = "Amy", onNameChange = {})
    }
}