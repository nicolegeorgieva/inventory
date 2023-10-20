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
import androidx.compose.material3.Surface
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
fun SortFilterRow() {
    val ascending = remember { mutableStateOf(true) }

    Row(modifier = Modifier.fillMaxWidth()) {
        Sort(ascending = ascending)

        Spacer(modifier = Modifier.weight(1f))

        CategoryFilter()
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
private fun CategoryFilter() {
    Column {
        val filter = remember { mutableStateOf("All") }
        val expanded = remember { mutableStateOf(false) }

        FilterButton(expanded = expanded, filter = filter)
        FilterMenu(expanded = expanded, filter = filter)
    }
}

@Composable
private fun FilterButton(
    expanded: MutableState<Boolean>,
    filter: MutableState<String>
) {
    TextButton(
        onClick = {
            expanded.value = true
        }
    ) {
        Text(filter.value)
        Icon(
            imageVector = Icons.Outlined.ArrowDropDown,
            contentDescription = "Dropdown menu"
        )
    }
}

@Composable
private fun FilterMenu(
    expanded: MutableState<Boolean>,
    filter: MutableState<String>
) {
    DropdownMenu(
        expanded = expanded.value,
        onDismissRequest = {
            expanded.value = false
        }
    ) {
        FilterMenuOption(name = "All", filter = filter, expanded = expanded)
        FilterMenuOption(name = "Groceries", filter = filter, expanded = expanded)
    }
}

@Composable
private fun FilterMenuOption(
    name: String,
    filter: MutableState<String>,
    expanded: MutableState<Boolean>
) {
    DropdownMenuItem(
        text = {
            Text(name)
        },
        onClick = {
            filter.value = name
            expanded.value = false
        }
    )
}

@Preview
@Composable
private fun SortFilterRowPreview() {
    InventoryTheme {
        Surface {
            SortFilterRow()
        }
    }
}