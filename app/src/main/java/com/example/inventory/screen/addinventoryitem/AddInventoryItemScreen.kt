package com.example.inventory.screen.addinventoryitem

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
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
import com.example.inventory.screen.addinventoryitem.component.AddInventoryItemButton
import com.example.inventory.screen.addinventoryitem.component.CategoryInput
import com.example.inventory.screen.addinventoryitem.component.InputRow
import com.example.inventory.screen.addinventoryitem.component.ItemImage
import com.example.inventory.ui.theme.InventoryTheme
import kotlinx.collections.immutable.persistentListOf

@Composable
fun AddInventoryItemScreen(
    navController: NavController,
    itemId: String?
) {
    val viewModel: AddInventoryItemViewModel = hiltViewModel()
    val uiState = viewModel.uiState()

    AddInventoryItemUi(
        navController = navController,
        uiState = uiState,
        onEvent = viewModel::onEvent
    )
}

@Composable
private fun AddInventoryItemUi(
    navController: NavController?,
    uiState: AddInventoryItemState,
    onEvent: (AddInventoryItemEvent) -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        topBar = {
            CustomTopAppBar(
                title = stringResource(R.string.add_inventory_item_title),
                navController = navController
            )
        },
        floatingActionButton = {
            AddInventoryItemButton(onEvent = onEvent)
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
    uiState: AddInventoryItemState,
    onEvent: (AddInventoryItemEvent) -> Unit
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
                    onEvent(AddInventoryItemEvent.SetName(it))
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
                    onEvent(AddInventoryItemEvent.SetQuantity(it))
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
                    onEvent(AddInventoryItemEvent.SetMinQuantityTarget(it))
                }
            )
        }

        item(key = "category") {
            CategoryInput(
                expanded = uiState.expanded,
                onExpandedChange = {
                    onEvent(AddInventoryItemEvent.OnExpandedChange)
                },
                category = uiState.category,
                categories = uiState.categories,
                onCategorySelected = {
                    onEvent(AddInventoryItemEvent.SetCategory(it))
                },
                openAddCategoryDialog = uiState.openAddCategoryDialog,
                newCategoryValue = uiState.newCategoryValue ?: "",
                onOpenCategoryDialog = {
                    onEvent(AddInventoryItemEvent.OnOpenCategoryDialog)
                },
                onNewCategoryValueChange = {
                    onEvent(AddInventoryItemEvent.OnNewCategoryValueChange(it))
                },
                onAddNewCategory = {
                    onEvent(AddInventoryItemEvent.SetCategory(it))
                },
                onCloseDialog = {
                    onEvent(AddInventoryItemEvent.OnCloseDialog)
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
                    onEvent(AddInventoryItemEvent.SetDescription(it))
                }
            )
        }

        item(key = "image") {
            ItemImage(
                imagePath = uiState.imagePath,
                link = uiState.link,
                onLinkValueChange = {
                    onEvent(AddInventoryItemEvent.OnLinkValueChange(it))
                },
                onRemoveImageClick = {
                    onEvent(AddInventoryItemEvent.SetLinkImage(null))
                },
                onAddLinkImageClick = {
                    onEvent(AddInventoryItemEvent.SetLinkImage(it))
                }
            )
        }
    }
}

@Preview
@Composable
private fun AddWithoutRequiredPreview() {
    InventoryTheme {
        AddInventoryItemUi(
            navController = null,
            uiState = AddInventoryItemState(
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
        AddInventoryItemUi(
            navController = null,
            uiState = AddInventoryItemState(
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