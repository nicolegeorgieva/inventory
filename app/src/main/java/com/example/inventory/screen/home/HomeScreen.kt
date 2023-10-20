package com.example.inventory.screen.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.inventory.R
import com.example.inventory.screen.home.component.HomeTopAppBar
import com.example.inventory.ui.theme.InventoryTheme
import kotlinx.collections.immutable.persistentListOf

@Composable
fun HomeScreen(navController: NavController) {
    val viewModel: HomeViewModel = hiltViewModel()
    val uiState = viewModel.uiState()

    HomeUi(
        navController = navController,
        uiState = uiState,
        onEvent = viewModel::onEvent
    )
}

@Composable
private fun HomeUi(
    navController: NavController?,
    uiState: HomeState,
    onEvent: (HomeEvent) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            HomeTopAppBar(name = uiState.name, navController = navController)
        },
        floatingActionButton = {
            AddButton(navController = navController)
        },
        floatingActionButtonPosition = FabPosition.Center,
        content = { innerPadding ->
            LazyColumn(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = innerPadding
            ) {
                if (!uiState.inventoryList.isNullOrEmpty()) {
                    item(key = "sort filter row") {
                        SortFilterRow()
                    }

                    items(uiState.inventoryList) { item ->
                        InventoryItemRow(
                            itemName = item.name,
                            quantity = item.quantity,
                            image = item.imagePath ?: "",
                            onAddQuantity = {
                                onEvent(HomeEvent.IncreaseQuantity(item.id))
                            },
                            onRemoveQuantity = {
                                onEvent(HomeEvent.DecreaseQuantity(item.id))
                            },
                        )
                    }
                } else {
                    item("empty inventory state") {
                        EmptyInventory()
                    }
                }
            }
        }
    )
}

@Composable
private fun AddButton(navController: NavController?) {
    FloatingActionButton(
        onClick = {
            navController?.navigate("addInventoryItem")
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
private fun SortFilterRow() {
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

@Composable
private fun InventoryItemRow(
    itemName: String,
    quantity: String,
    image: String,
    onAddQuantity: () -> Unit,
    onRemoveQuantity: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .border(
                border = BorderStroke(0.5.dp, MaterialTheme.colorScheme.onBackground),
                shape = RoundedCornerShape(4.dp)
            ),
        shape = RoundedCornerShape(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(image = image)

            ItemName(itemName = itemName)

            AddRemoveQuantity(
                onRemoveQuantity = onRemoveQuantity,
                quantity = quantity,
                onAddQuantity = onAddQuantity
            )
        }
    }
}

@Composable
private fun Image(
    image: String
) {
    val modifier = Modifier
        .size(64.dp)
        .background(MaterialTheme.colorScheme.background)
        .border(
            border = BorderStroke(0.5.dp, MaterialTheme.colorScheme.onBackground),
            shape = RoundedCornerShape(
                topStart = 4.dp,
                topEnd = 0.dp,
                bottomStart = 4.dp,
                bottomEnd = 0.dp
            )
        )
        .padding(4.dp)

    if (image.isNotBlank()) {
        AsyncImage(
            modifier = modifier,
            model = image,
            contentDescription = stringResource(R.string.item_image)
        )
    } else {
        Icon(
            modifier = modifier,
            painter = painterResource(id = R.drawable.baseline_inventory_24),
            contentDescription = stringResource(R.string.item_image),
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun RowScope.ItemName(itemName: String) {
    Text(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .weight(1f),
        text = itemName
    )
}

@Composable
private fun AddRemoveQuantity(
    onRemoveQuantity: () -> Unit,
    quantity: String,
    onAddQuantity: () -> Unit
) {
    RemoveQuantityButton(
        imagePainter = painterResource(id = R.drawable.baseline_remove_circle_24),
        contentDescription = stringResource(R.string.remove),
        onClick = onRemoveQuantity
    )

    Text(
        modifier = Modifier.widthIn(min = 24.dp),
        textAlign = TextAlign.Center,
        text = quantity
    )

    AddQuantityButton(
        imageVector = Icons.Filled.AddCircle,
        contentDescription = stringResource(R.string.add),
        onClick = onAddQuantity
    )
}

@Composable
private fun AddQuantityButton(
    imageVector: ImageVector,
    contentDescription: String,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription
        )
    }
}

@Composable
private fun RemoveQuantityButton(
    imagePainter: Painter,
    contentDescription: String,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick
    ) {
        Icon(
            painter = imagePainter,
            contentDescription = contentDescription
        )
    }
}

@Composable
private fun EmptyInventory() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmptyInventoryMessage()

        LottieAnimation()
    }
}

@Composable
private fun EmptyInventoryMessage() {
    Text(
        text = stringResource(R.string.home_empty_inventory),
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.headlineSmall
    )
}

@Composable
private fun LottieAnimation() {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.empty_inventory)
    )

    LottieAnimation(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        composition = composition,
        iterations = Int.MAX_VALUE
    )
}

@Preview(showBackground = true)
@Composable
private fun HomeGenericEmptyPreview() {
    InventoryTheme {
        HomeUi(
            navController = null,
            uiState = HomeState(
                name = null,
                inventoryList = null
            ),
            onEvent = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomePersonalizedEmptyPreview() {
    InventoryTheme {
        HomeUi(
            navController = null,
            uiState = HomeState(
                name = "Amy",
                inventoryList = null
            ),
            onEvent = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomePersonalizedInventoryPreview() {
    InventoryTheme {
        HomeUi(
            navController = null,
            uiState = HomeState(
                name = "Amy",
                inventoryList = persistentListOf(
                    InventoryUi(
                        id = "",
                        name = "Water bottles",
                        quantity = "5",
                        imagePath = null,
                        category = null
                    ),
                    InventoryUi(
                        id = "",
                        name = "Kitchen roll",
                        quantity = "4",
                        imagePath = null,
                        category = null
                    ),
                    InventoryUi(
                        id = "",
                        name = "Soap",
                        quantity = "6",
                        imagePath = null,
                        category = null
                    ),
                    InventoryUi(
                        id = "",
                        name = "Shampoo",
                        quantity = "2",
                        imagePath = null,
                        category = null
                    ),
                    InventoryUi(
                        id = "",
                        name = "Liquid laundry detergent",
                        quantity = "1",
                        imagePath = null,
                        category = null
                    ),
                    InventoryUi(
                        id = "",
                        name = "Dishwasher tablets pack",
                        quantity = "1",
                        imagePath = null,
                        category = null
                    ),
                    InventoryUi(
                        id = "",
                        name = "Shower gel",
                        quantity = "3",
                        imagePath = null,
                        category = null
                    ),
                    InventoryUi(
                        id = "",
                        name = "Glass cleaner",
                        quantity = "1",
                        imagePath = null,
                        category = null
                    ),
                    InventoryUi(
                        id = "",
                        name = "Floor cleaner",
                        quantity = "1",
                        imagePath = null,
                        category = null
                    ),
                    InventoryUi(
                        id = "",
                        name = "Cotton buds pack",
                        quantity = "3",
                        imagePath = null,
                        category = null
                    )
                )
            ),
            onEvent = {}
        )
    }
}