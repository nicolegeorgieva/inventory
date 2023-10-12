package com.example.inventory.screen.addinventoryitem

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.inventory.R
import com.example.inventory.component.BackButton
import com.example.inventory.ui.theme.InventoryTheme

@Composable
fun AddInventoryItemScreen(navController: NavController) {
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
            TopAppBar(navController = navController)
        },
        floatingActionButton = {
            AddButton(onEvent = onEvent)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBar(navController: NavController?) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.add_inventory_item_title),
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            BackButton {
                navController?.popBackStack()
            }
        }
    )
}

@Composable
private fun AddButton(onEvent: (AddInventoryItemEvent) -> Unit) {
    FloatingActionButton(
        onClick = {
            onEvent(AddInventoryItemEvent.AddInventoryItem)
        }
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(R.string.home_add)
        )
    }
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
                onInputChange = {
                    onEvent(AddInventoryItemEvent.SetMinQuantityTarget(it))
                }
            )
        }

        item(key = "category") {
            InputRow(
                label = stringResource(R.string.category_label),
                input = uiState.category ?: "",
                onInputChange = {
                    onEvent(AddInventoryItemEvent.SetCategory(it))
                }
            )
        }

        item(key = "description") {
            InputRow(
                modifier = Modifier.height(124.dp),
                label = stringResource(R.string.description_label),
                input = uiState.description ?: "",
                onInputChange = {
                    onEvent(AddInventoryItemEvent.SetDescription(it))
                }
            )
        }

        item(key = "image") {
            ItemImage(uiState = uiState) {}
        }
    }
}

@Composable
private fun InputRow(
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    label: String,
    input: String,
    onInputChange: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        TextField(
            modifier = modifier,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            value = input,
            onValueChange = onInputChange,
            label = {
                Text(text = label)
            }
        )
    }
}

@Composable
private fun ItemImage(
    uiState: AddInventoryItemState,
    onClick: () -> Unit
) {
    if (uiState.imagePath == null) {
        AddImage(onClick = onClick)
    } else {
        ImageFromPath(path = uiState.imagePath)
    }
}

@Composable
private fun AddImage(onClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(R.string.add_image_label))

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedIconButton(
            modifier = Modifier.size(224.dp),
            shape = RectangleShape,
            onClick = onClick
        ) {
            Icon(
                imageVector = Icons.Outlined.AddCircle,
                contentDescription = stringResource(R.string.add_image_label)
            )
        }
    }
}

@Composable
private fun ImageFromPath(path: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = Modifier.size(224.dp),
            model = path,
            contentDescription = stringResource(R.string.item_image)
        )
    }
}

@Preview
@Composable
private fun EmptyStatePreview() {
    InventoryTheme {
        AddInventoryItemUi(
            navController = null,
            uiState = AddInventoryItemState(
                name = null,
                quantity = null,
                minQuantityTarget = null,
                category = null,
                description = null,
                imagePath = null
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
                description = null,
                imagePath = null
            ),
            onEvent = {}
        )
    }
}