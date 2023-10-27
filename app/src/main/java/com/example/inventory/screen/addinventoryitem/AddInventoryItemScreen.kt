package com.example.inventory.screen.addinventoryitem

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.inventory.R
import com.example.inventory.component.CustomTopAppBar
import com.example.inventory.ui.theme.InventoryTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

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
            CustomTopAppBar(
                title = stringResource(R.string.add_inventory_item_title),
                navController = navController
            )
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

@Composable
private fun AddButton(
    onEvent: (AddInventoryItemEvent) -> Unit
) {
    FloatingActionButton(
        onClick = {
            onEvent(AddInventoryItemEvent.AddInventoryItem)
        },
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(R.string.add)
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
            CategoryInputRow(
                expanded = uiState.expanded,
                onExpandedChange = {
                    onEvent(AddInventoryItemEvent.OnExpandedChange)
                },
                categories = uiState.categories,
                onCategorySelected = {
                    onEvent(AddInventoryItemEvent.SetCategory(it))
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
                uiState = uiState,
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

@Composable
private fun CategoryInputRow(
    expanded: Boolean,
    onExpandedChange: () -> Unit,
    categories: ImmutableList<String>,
    onCategorySelected: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(top = 8.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
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
                text = "Category: None"
            )

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Category dropdown menu"
            )
        }

        DropdownMenu(
            modifier = Modifier
                .widthIn(OutlinedTextFieldDefaults.MinWidth)
                .heightIn(OutlinedTextFieldDefaults.MinHeight),
            offset = DpOffset(x = 52.dp, y = 4.dp),
            expanded = expanded,
            onDismissRequest = onExpandedChange
        ) {
            CategoryDropdownMenuOption(
                category = "None",
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
        }
    }
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
private fun ItemImage(
    uiState: AddInventoryItemState,
    onLinkValueChange: (String) -> Unit,
    onAddLinkImageClick: (String) -> Unit,
    onRemoveImageClick: () -> Unit
) {
    if (uiState.imagePath == null) {
        AddImageSection(
            linkValue = uiState.link ?: "",
            onLinkValueChange = onLinkValueChange,
            onAddLinkImageClick = onAddLinkImageClick
        )
    } else {
        ImageFromPath(
            path = uiState.imagePath,
            onRemoveImageClick = onRemoveImageClick
        )
    }
}

@Composable
private fun AddImageSection(
    linkValue: String,
    onLinkValueChange: (String) -> Unit,
    onAddLinkImageClick: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(R.string.add_image_label))

        Spacer(modifier = Modifier.height(6.dp))

        AddImageFromLink(
            linkValue = linkValue,
            onLinkValueChange = onLinkValueChange,
            onAddLinkImage = onAddLinkImageClick
        )
    }
}

@Composable
private fun AddImageFromLink(
    linkValue: String,
    onLinkValueChange: (String) -> Unit,
    onAddLinkImage: (String) -> Unit
) {
    InputRow(
        input = linkValue,
        onInputChange = onLinkValueChange,
        imeAction = ImeAction.Done,
        label = stringResource(R.string.image_url_label)
    )

    if (linkValue.isNotBlank()) {
        Spacer(modifier = Modifier.height(8.dp))

        AddImageUrlButton(onAddLinkImage = {
            onAddLinkImage(linkValue)
        })
    }
}

@Composable
private fun InputRow(
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    label: String? = null,
    input: String,
    supportingText: String? = null,
    addWithoutRequired: Boolean = false,
    onInputChange: (String) -> Unit
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
                        color = if (addWithoutRequired) {
                            MaterialTheme.colorScheme.error
                        } else {
                            MaterialTheme.colorScheme.onBackground
                        }
                    )
                }
            } else {
                null
            }
        )
    }
}

@Composable
private fun AddImageUrlButton(onAddLinkImage: () -> Unit) {
    Button(
        onClick = onAddLinkImage,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        )
    ) {
        Text(stringResource(R.string.add))
    }
}

@Composable
private fun ImageFromPath(path: String, onRemoveImageClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Top
    ) {
        AsyncImage(
            modifier = Modifier.size(196.dp),
            model = path,
            contentDescription = stringResource(R.string.item_image)
        )

        FilledIconButton(
            onClick = onRemoveImageClick,
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = MaterialTheme.colorScheme.onBackground
            )
        ) {
            Icon(
                imageVector = Icons.Outlined.Clear,
                contentDescription = stringResource(R.string.add_inventory_item_remove_image)
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
                addWithoutRequired = true
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
                addWithoutRequired = false
            ),
            onEvent = {}
        )
    }
}