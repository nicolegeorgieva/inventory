package com.example.inventory.screen.addoreditinventoryitem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.inventory.R
import com.example.inventory.ui.theme.InventoryTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun CategoryInput(
    categoryDropdownMenuExpanded: Boolean,
    openAddCategoryDialog: Boolean,
    category: String?,
    newCategoryValue: String,
    onOpenCategoryDialog: () -> Unit,
    onNewCategoryValueChange: (String) -> Unit,
    onAddNewCategory: (String) -> Unit,
    onCloseDialog: () -> Unit,
    onExpandedChange: () -> Unit,
    categories: ImmutableList<String>,
    onCategorySelected: (String?) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(top = 8.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CategoryRow(
            category = category ?: stringResource(R.string.none),
            onExpandedChange = onExpandedChange
        )

        CategoryDropdownMenu(
            expanded = categoryDropdownMenuExpanded,
            onOpenCategoryDialog = onOpenCategoryDialog,
            onExpandedChange = onExpandedChange,
            categories = categories,
            onCategorySelected = onCategorySelected
        )

        if (openAddCategoryDialog) {
            AddNewCategoryDialog(
                newCategoryValue = newCategoryValue,
                onNewCategoryValueChange = onNewCategoryValueChange,
                onAddNewCategory = onAddNewCategory,
                onCloseDialog = onCloseDialog
            )
        }
    }
}

@Composable
private fun CategoryRow(
    category: String,
    onExpandedChange: () -> Unit
) {
    Row(
        modifier = Modifier
            .width(OutlinedTextFieldDefaults.MinWidth)
            .height(OutlinedTextFieldDefaults.MinHeight)
            .border(
                border = BorderStroke(
                    OutlinedTextFieldDefaults.UnfocusedBorderThickness,
                    MaterialTheme.colorScheme.outline
                ),
                shape = OutlinedTextFieldDefaults.shape
            )
            .clickable {
                onExpandedChange()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = stringResource(R.string.category, category)
        )

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            imageVector = Icons.Default.ArrowDropDown,
            contentDescription = stringResource(R.string.category_dropdown_menu)
        )
    }
}

@Composable
private fun CategoryDropdownMenu(
    expanded: Boolean,
    onOpenCategoryDialog: () -> Unit,
    onExpandedChange: () -> Unit,
    categories: ImmutableList<String>,
    onCategorySelected: (String?) -> Unit
) {
    DropdownMenu(
        modifier = Modifier
            .widthIn(OutlinedTextFieldDefaults.MinWidth)
            .heightIn(OutlinedTextFieldDefaults.MinHeight),
        offset = DpOffset(x = 52.dp, y = 4.dp),
        expanded = expanded,
        onDismissRequest = onExpandedChange
    ) {
        CategoryDropDownNoneOption(
            onCategorySelected = onCategorySelected,
            onMenuExpandedChange = onExpandedChange
        )

        categories.forEach {
            CategoryDropdownMenuOption(
                category = it,
                onCategorySelected = onCategorySelected,
                onMenuExpandedChange = onExpandedChange
            )
        }

        AddCategoryDropdownMenuOption(
            onAddNewSelected = onOpenCategoryDialog
        )
    }
}

@Composable
private fun CategoryDropDownNoneOption(
    onCategorySelected: (String?) -> Unit,
    onMenuExpandedChange: () -> Unit
) {
    DropdownMenuItem(
        modifier = Modifier.padding(horizontal = 24.dp),
        text = {
            Text(stringResource(R.string.none))
        },
        onClick = {
            onCategorySelected(null)
            onMenuExpandedChange()
        }
    )
}

@Composable
private fun CategoryDropdownMenuOption(
    category: String,
    onCategorySelected: (String) -> Unit,
    onMenuExpandedChange: () -> Unit
) {
    DropdownMenuItem(
        modifier = Modifier.padding(horizontal = 24.dp),
        text = {
            Text(category)
        },
        onClick = {
            onCategorySelected(category)
            onMenuExpandedChange()
        }
    )
}

@Composable
private fun AddCategoryDropdownMenuOption(
    onAddNewSelected: () -> Unit
) {
    DropdownMenuItem(
        modifier = Modifier.padding(horizontal = 24.dp),
        text = {
            Text(stringResource(R.string.add_new))
        },
        onClick = onAddNewSelected,
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(R.string.add_new)
            )
        }
    )
}

@Composable
private fun AddNewCategoryDialog(
    newCategoryValue: String,
    onNewCategoryValueChange: (String) -> Unit,
    onAddNewCategory: (String) -> Unit,
    onCloseDialog: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onCloseDialog,
        icon = {
            Icon(
                imageVector = Icons.Filled.List,
                contentDescription = stringResource(R.string.list)
            )
        },
        title = {
            Text(text = stringResource(R.string.add_new_category))
        },
        text = {
            OutlinedTextField(
                value = newCategoryValue,
                onValueChange = onNewCategoryValueChange,
                label = {
                    Text(stringResource(R.string.new_category))
                }
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onAddNewCategory(newCategoryValue)
                }
            ) {
                Text(stringResource(R.string.add))
            }
        },
        dismissButton = {
            IconButton(
                onClick = onCloseDialog
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = stringResource(R.string.close)
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun CategoryInputPreview() {
    InventoryTheme {
        CategoryInput(
            categoryDropdownMenuExpanded = false,
            openAddCategoryDialog = false,
            newCategoryValue = "",
            onOpenCategoryDialog = {},
            onNewCategoryValueChange = {},
            onAddNewCategory = {},
            onCloseDialog = {},
            onExpandedChange = {},
            category = null,
            categories = persistentListOf(),
            onCategorySelected = {}
        )
    }
}