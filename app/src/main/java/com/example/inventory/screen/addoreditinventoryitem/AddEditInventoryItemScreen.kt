package com.example.inventory.screen.addoreditinventoryitem

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.inventory.R
import com.example.inventory.component.CustomTopAppBar
import com.example.inventory.screen.addoreditinventoryitem.component.AddUpdateInventoryItemButton
import com.example.inventory.screen.addoreditinventoryitem.component.CategoryInput
import com.example.inventory.screen.addoreditinventoryitem.component.InputRow
import com.example.inventory.screen.addoreditinventoryitem.component.ItemImage
import com.example.inventory.ui.theme.InventoryTheme
import kotlinx.collections.immutable.persistentListOf

@Composable
fun AddEditInventoryItemScreen(
    navController: NavController,
    itemId: String?
) {
    val viewModel: AddEditInventoryItemViewModel = hiltViewModel()
    val uiState = viewModel.uiState()

    if (itemId != null) {
        LaunchedEffect(Unit) {
            viewModel.onEvent(AddEditInventoryItemEvent.LoadItem(itemId))
        }
    }

    AddEditInventoryItemUi(
        navController = navController,
        uiState = uiState,
        itemId = itemId,
        onEvent = viewModel::onEvent
    )
}

@Composable
private fun AddEditInventoryItemUi(
    itemId: String?,
    navController: NavController?,
    uiState: AddEditInventoryItemState,
    onEvent: (AddEditInventoryItemEvent) -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        topBar = {
            CustomTopAppBar(
                title = if (itemId == null) {
                    stringResource(R.string.add_inventory_item_title)
                } else {
                    "Edit Inventory Item"
                },
                navController = navController,
                action = {
                    IconButton(onClick = {
                        onEvent(AddEditInventoryItemEvent.DeleteItem)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            AddUpdateInventoryItemButton(
                itemId = itemId,
                onEvent = onEvent
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        content = { innerPadding ->
            Content(
                innerPadding = innerPadding,
                uiState = uiState,
                onEvent = onEvent
            )
        }
    )
}

@Composable
private fun Content(
    innerPadding: PaddingValues,
    uiState: AddEditInventoryItemState,
    onEvent: (AddEditInventoryItemEvent) -> Unit
) {
    LazyColumn(
        modifier = Modifier.padding(top = 12.dp),
        contentPadding = innerPadding,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item(key = "name") {
            InputRow(
                label = stringResource(R.string.name_label),
                input = uiState.name ?: "",
                supportingText = stringResource(R.string.required_label),
                addWithoutRequired = uiState.addWithoutRequired,
                onInputChange = {
                    onEvent(AddEditInventoryItemEvent.SetName(it))
                }
            )
        }

        item(key = "quantity") {
            InputRow(
                label = stringResource(R.string.quantity_label),
                keyboardType = KeyboardType.Number,
                input = uiState.quantity ?: "",
                supportingText = stringResource(R.string.required_integer_number_label),
                addWithoutRequired = uiState.addWithoutRequired,
                onInputChange = {
                    onEvent(AddEditInventoryItemEvent.SetQuantity(it))
                }
            )
        }

        item(key = "min quantity target") {
            InputRow(
                label = stringResource(R.string.min_quantity_target_label),
                keyboardType = KeyboardType.Number,
                input = uiState.minQuantityTarget ?: "",
                supportingText = stringResource(R.string.required_integer_number_label),
                addWithoutRequired = uiState.addWithoutRequired,
                onInputChange = {
                    onEvent(AddEditInventoryItemEvent.SetMinQuantityTarget(it))
                }
            )
        }

        item(key = "category") {
            CategoryInput(
                expanded = uiState.expanded,
                onExpandedChange = {
                    onEvent(AddEditInventoryItemEvent.OnExpandedChange)
                },
                category = uiState.category,
                categories = uiState.categories,
                onCategorySelected = {
                    onEvent(AddEditInventoryItemEvent.SetCategory(it))
                },
                openAddCategoryDialog = uiState.openAddCategoryDialog,
                newCategoryValue = uiState.newCategoryValue ?: "",
                onOpenCategoryDialog = {
                    onEvent(AddEditInventoryItemEvent.OnOpenCategoryDialog)
                },
                onNewCategoryValueChange = {
                    onEvent(AddEditInventoryItemEvent.OnNewCategoryValueChange(it))
                },
                onAddNewCategory = {
                    onEvent(AddEditInventoryItemEvent.SetCategory(it))
                },
                onCloseDialog = {
                    onEvent(AddEditInventoryItemEvent.OnCloseDialog)
                }
            )
        }

        item(key = "description") {
            InputRow(
                modifier = Modifier.height(124.dp),
                label = stringResource(R.string.description_label),
                input = uiState.description ?: "",
                imeAction = ImeAction.Default,
                onInputChange = {
                    onEvent(AddEditInventoryItemEvent.SetDescription(it))
                }
            )
        }

        item(key = "image") {
            ItemImage(
                imagePath = uiState.imagePath,
                link = uiState.link,
                onLinkValueChange = {
                    onEvent(AddEditInventoryItemEvent.OnLinkValueChange(it))
                },
                onRemoveImageClick = {
                    onEvent(AddEditInventoryItemEvent.SetLinkImage(null))
                },
                onAddLinkImageClick = {
                    onEvent(AddEditInventoryItemEvent.SetLinkImage(it))
                }
            )
        }
    }
}

@Preview
@Composable
private fun AddWithoutRequiredPreview() {
    InventoryTheme {
        AddEditInventoryItemUi(
            navController = null,
            itemId = null,
            uiState = AddEditInventoryItemState(
                name = null,
                quantity = null,
                minQuantityTarget = null,
                category = "All",
                categories = persistentListOf(),
                expanded = false,
                description = null,
                link = null,
                imagePath = null,
                addWithoutRequired = true,
                newCategoryValue = "",
                openAddCategoryDialog = false
            ),
            onEvent = {}
        )
    }
}

@Preview
@Composable
private fun FilledStatePreview() {
    InventoryTheme {
        AddEditInventoryItemUi(
            navController = null,
            itemId = null,
            uiState = AddEditInventoryItemState(
                name = "Watter bottles",
                quantity = "5",
                minQuantityTarget = "5",
                category = "Groceries",
                categories = persistentListOf(),
                expanded = false,
                description = null,
                link = null,
                imagePath = null,
                addWithoutRequired = false,
                newCategoryValue = "",
                openAddCategoryDialog = false
            ),
            onEvent = {}
        )
    }
}