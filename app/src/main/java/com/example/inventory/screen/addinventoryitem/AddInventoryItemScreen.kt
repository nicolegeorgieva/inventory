package com.example.inventory.screen.addinventoryitem

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddInventoryItemUi(
    navController: NavController?,
    uiState: AddInventoryItemState,
    onEvent: (AddInventoryItemEvent) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

            MediumTopAppBar(
                title = {
                    Text(
                        text = "Create Inventory Item",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                actions = {
                    BackButton {
                        navController?.popBackStack()
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
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
        },
        floatingActionButtonPosition = FabPosition.End,
        content = { innerPadding ->
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = innerPadding
            ) {
                item(key = "name") {
                    InputRow(
                        label = "Name",
                        input = uiState.name ?: "",
                        onInputChange = {
                            onEvent(AddInventoryItemEvent.SetName(it))
                        }
                    )
                }

                item(key = "quantity") {
                    InputRow(
                        label = "Quantity",
                        input = uiState.quantity ?: "",
                        onInputChange = {
                            onEvent(AddInventoryItemEvent.SetQuantity(it))
                        }
                    )
                }

                item(key = "min quantity target") {
                    InputRow(
                        label = "Min Quantity Target",
                        input = uiState.minQuantityTarget ?: "",
                        onInputChange = {
                            onEvent(AddInventoryItemEvent.SetMinQuantityTarget(it))
                        }
                    )
                }

                item(key = "category") {
                    InputRow(
                        label = "Category",
                        input = uiState.category ?: "",
                        onInputChange = {
                            onEvent(AddInventoryItemEvent.SetCategory(it))
                        }
                    )
                }

                item(key = "description") {
                    InputRow(
                        label = "Description",
                        input = uiState.description ?: "",
                        onInputChange = {
                            onEvent(AddInventoryItemEvent.SetDescription(it))
                        }
                    )
                }

                item(key = "image") {
                    InputRow(
                        label = "Image",
                        input = uiState.image ?: "",
                        onInputChange = {
                            onEvent(AddInventoryItemEvent.SetImage(it))
                        }
                    )
                }
            }
        }
    )
}

@Composable
private fun InputRow(
    label: String,
    input: String,
    onInputChange: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label)

        Spacer(modifier = Modifier.width(8.dp))

        TextField(value = input, onValueChange = onInputChange)
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
                image = null
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
                image = null
            ),
            onEvent = {}
        )
    }
}