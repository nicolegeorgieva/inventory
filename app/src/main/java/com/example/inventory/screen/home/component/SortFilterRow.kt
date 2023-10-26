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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.inventory.R
import com.example.inventory.ui.theme.InventoryTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun SortFilterRow(
    sortByAscending: Boolean,
    onSortOptionClicked: () -> Unit,
    category: String,
    categories: ImmutableList<String>,
    menuExpanded: Boolean,
    onOptionSelected: (String) -> Unit,
    onExpandedChange: (Boolean) -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Sort(sortByAscending = sortByAscending, onSortOptionClicked = onSortOptionClicked)

        Spacer(modifier = Modifier.weight(1f))

        CategoryFilter(
            category = category,
            categories = categories,
            menuExpanded = menuExpanded,
            onCategorySelected = onOptionSelected,
            onExpandedChange = onExpandedChange
        )
    }
}

@Composable
private fun Sort(
    sortByAscending: Boolean,
    onSortOptionClicked: () -> Unit
) {
    Column {
        TextButton(
            onClick = onSortOptionClicked
        ) {
            Text(stringResource(R.string.home_sort_by))
            if (sortByAscending) {
                Icon(
                    imageVector = Icons.Outlined.KeyboardArrowUp,
                    contentDescription = stringResource(R.string.home_sort_by_ascending)
                )
            } else {
                Icon(
                    imageVector = Icons.Outlined.KeyboardArrowDown,
                    contentDescription = stringResource(R.string.home_sort_by_descending)
                )
            }
        }
    }
}

@Composable
private fun CategoryFilter(
    category: String,
    categories: ImmutableList<String>,
    menuExpanded: Boolean,
    onCategorySelected: (String) -> Unit,
    onExpandedChange: (Boolean) -> Unit
) {
    Column {
        FilterButton(
            category = category,
            onExpandedChange = onExpandedChange
        )

        FilterMenu(
            expanded = menuExpanded,
            categories = categories,
            onCategorySelected = onCategorySelected,
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
            contentDescription = stringResource(R.string.home_dropdown_menu)
        )
    }
}

@Composable
private fun FilterMenu(
    expanded: Boolean,
    categories: ImmutableList<String>,
    onCategorySelected: (String) -> Unit,
    onExpandedChange: (Boolean) -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = {
            onExpandedChange(false)
        }
    ) {
        FilterMenuOption(
            category = "All",
            onCategorySelected = onCategorySelected,
            onMenuExpandedChange = onExpandedChange
        )

        categories.forEach {
            FilterMenuOption(
                category = it,
                onCategorySelected = onCategorySelected,
                onMenuExpandedChange = onExpandedChange
            )
        }
    }
}

@Composable
private fun FilterMenuOption(
    category: String,
    onCategorySelected: (String) -> Unit,
    onMenuExpandedChange: (Boolean) -> Unit
) {
    DropdownMenuItem(
        text = {
            Text(category)
        },
        onClick = {
            onCategorySelected(category)
            onMenuExpandedChange(false)
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun SortFilterRowPreview() {
    InventoryTheme {
        SortFilterRow(
            sortByAscending = true,
            onSortOptionClicked = {},
            category = "All",
            categories = persistentListOf("Groceries"),
            menuExpanded = false,
            onOptionSelected = {},
            onExpandedChange = {}
        )
    }
}