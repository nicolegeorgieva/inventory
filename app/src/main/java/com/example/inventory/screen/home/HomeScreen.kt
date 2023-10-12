package com.example.inventory.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.inventory.R
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeUi(
    navController: NavController?,
    uiState: HomeState,
    onEvent: (HomeEvent) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

            MediumTopAppBar(
                title = {
                    Text(
                        text = if (uiState.name == null) {
                            stringResource(R.string.home_generic_greeting)
                        } else {
                            "${stringResource(R.string.home_personalized_greeting)}, ${uiState.name}"
                        },
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            navController?.navigate("moreMenu")
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = stringResource(R.string.home_menu)
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController?.navigate("addInventoryItem")
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.home_add)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        content = { innerPadding ->
            LazyColumn(
                modifier = Modifier.padding(12.dp),
                contentPadding = innerPadding
            ) {
                if (uiState.inventoryList != null) {
                    items(uiState.inventoryList) { item ->
                        InventoryItemRow(itemName = item.name, quantity = item.quantity)
                    }
                } else {
                    item {
                        EmptyInventory()
                    }
                }
            }
        }
    )
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
        Text(
            text = stringResource(R.string.home_empty_inventory),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineSmall
        )

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
}

@Composable
private fun InventoryItemRow(itemName: String, quantity: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = itemName)

        Text(text = quantity)

        AddOrRemoveQuantityButton(
            imageVector = Icons.Filled.AddCircle,
            contentDescription = "Add"
        ) {}

        AddOrRemoveQuantityButton(
            imageVector = Icons.Filled.Clear,
            contentDescription = "Remove"
        ) {}
    }
}

@Composable
private fun AddOrRemoveQuantityButton(
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
                        imageUrl = null,
                        category = null
                    ),
                    InventoryUi(
                        id = "",
                        name = "Kitchen roll",
                        quantity = "4",
                        imageUrl = null,
                        category = null
                    ),
                    InventoryUi(
                        id = "",
                        name = "Soap",
                        quantity = "6",
                        imageUrl = null,
                        category = null
                    ),
                    InventoryUi(
                        id = "",
                        name = "Shampoo",
                        quantity = "2",
                        imageUrl = null,
                        category = null
                    ),
                    InventoryUi(
                        id = "",
                        name = "Liquid laundry detergent",
                        quantity = "1",
                        imageUrl = null,
                        category = null
                    ),
                    InventoryUi(
                        id = "",
                        name = "Dishwasher tablets pack",
                        quantity = "1",
                        imageUrl = null,
                        category = null
                    ),
                    InventoryUi(
                        id = "",
                        name = "Shower gel",
                        quantity = "3",
                        imageUrl = null,
                        category = null
                    ),
                    InventoryUi(
                        id = "",
                        name = "Glass cleaner",
                        quantity = "1",
                        imageUrl = null,
                        category = null
                    ),
                    InventoryUi(
                        id = "",
                        name = "Floor cleaner",
                        quantity = "1",
                        imageUrl = null,
                        category = null
                    ),
                    InventoryUi(
                        id = "",
                        name = "Cotton buds pack",
                        quantity = "3",
                        imageUrl = null,
                        category = null
                    )
                )
            ),
            onEvent = {}
        )
    }
}