package com.example.inventory.screen.addoreditinventoryitem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.example.inventory.ui.theme.InventoryTheme

@Composable
fun InputRow(
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    input: String,
    onInputChange: (String) -> Unit,
    label: String? = null,
    supportingText: String? = null,
    valid: Boolean = true
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            modifier = modifier,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            value = input,
            onValueChange = onInputChange,
            label = {
                if (label != null) {
                    Text(text = label)
                }
            },
            supportingText = if (supportingText != null) {
                {
                    Text(
                        text = supportingText,
                        color = if (valid) {
                            MaterialTheme.colorScheme.onBackground
                        } else {
                            MaterialTheme.colorScheme.error
                        }
                    )
                }
            } else {
                null
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BlankInputRowPreview() {
    InventoryTheme {
        InputRow(
            input = "",
            label = "Name",
            onInputChange = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FilledInputRowPreview() {
    InventoryTheme {
        InputRow(
            input = "Amy",
            label = "Name",
            onInputChange = {}
        )
    }
}