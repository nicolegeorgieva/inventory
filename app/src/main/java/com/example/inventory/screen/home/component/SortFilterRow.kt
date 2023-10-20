package com.example.inventory.screen.home.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.inventory.ui.theme.InventoryTheme

@Composable
fun SortFilterRow(
    category: String,
    menuExpanded: Boolean,
    onOptionSelected: (String) -> Unit,
    onExpandedChange: (Boolean) -> Unit
) {
    val ascending = remember { mutableStateOf(true) }

    Row(modifier = Modifier.fillMaxWidth()) {
        Sort(ascending = ascending)

        Spacer(modifier = Modifier.weight(1f))

        CategoryFilter(
            category = category,
            menuExpanded = menuExpanded,
            onOptionSelected = onOptionSelected,
            onExpandedChange = onExpandedChange
        )
    }
}

@Composable
private fun Sort(
    ascending: MutableState<Boolean>
) {
    Column {
        TextButton(
            onClick = {
                ascending.value = !ascending.value
            }
        ) {
            Text("Sort by:")
            if (ascending.value) {
                Icon(
                    imageVector = Icons.Outlined.KeyboardArrowUp,
                    contentDescription = "Ascending"
                )
            } else {
                Icon(
                    imageVector = Icons.Outlined.KeyboardArrowDown,
                    contentDescription = "Descending"
                )
            }
        }
    }
}

@Composable
private fun CategoryFilter(
    category: String,
    menuExpanded: Boolean,
    onOptionSelected: (String) -> Unit,
    onExpandedChange: (Boolean) -> Unit
) {
    Column {
        FilterButton(
            category = category,
            onExpandedChange = onExpandedChange
        )

        FilterMenu(
            expanded = menuExpanded,
            onOptionSelected = onOptionSelected,
            onExpandedChange = onExpandedChange
        )
    }
}

@Composable
private fun FilterButton(
    category: String,
    onExpandedChange: (Boolean) -> Unit
) {
    TextButton(
        onClick = {
            onExpandedChange(true)
        }
    ) {
        Text(category)
        Icon(
            imageVector = Icons.Outlined.ArrowDropDown,
            contentDescription = "Dropdown menu"
        )
    }
}

@Composable
private fun FilterMenu(
    expanded: Boolean,
    onOptionSelected: (String) -> Unit,
    onExpandedChange: (Boolean) -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = {
            onExpandedChange(false)
        }
    ) {
        FilterMenuOption(
            option = "All",
            onOptionSelected = onOptionSelected,
            onMenuExpandedChange = onExpandedChange
        )

        FilterMenuOption(
            option = "Groceries",
            onOptionSelected = onOptionSelected,
            onMenuExpandedChange = onExpandedChange
        )
    }
}

@Composable
private fun FilterMenuOption(
    option: String,
    onOptionSelected: (String) -> Unit,
    onMenuExpandedChange: (Boolean) -> Unit
) {
    DropdownMenuItem(
        text = {
            Text(option)
        },
        onClick = {
            onOptionSelected(option)
            onMenuExpandedChange(false)
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun SortFilterRowPreview() {
    InventoryTheme {
        SortFilterRow(
            category = "All",
            menuExpanded = false,
            onOptionSelected = {},
            onExpandedChange = {}
        )
    }
}