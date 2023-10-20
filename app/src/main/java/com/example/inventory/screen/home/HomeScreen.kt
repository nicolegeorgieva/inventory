package com.example.inventory.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.inventory.R
import com.example.inventory.screen.home.component.HomeTopAppBar
import com.example.inventory.screen.home.component.InventoryItemRow
import com.example.inventory.screen.home.component.SortFilterRow
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