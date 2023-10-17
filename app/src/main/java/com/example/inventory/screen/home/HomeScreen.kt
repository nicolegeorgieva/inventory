package com.example.inventory.screen.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
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

@Composable
private fun HomeUi(
    navController: NavController?,
    uiState: HomeState,
    onEvent: (HomeEvent) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            HomeTopAppBar(uiState = uiState, navController = navController)
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
                    items(uiState.inventoryList) { item ->
                        InventoryItemRow(
                            itemName = item.name,
                            quantity = item.quantity,
                            image = item.imagePath ?: "",
                            onAddQuantity = {
                                onEvent(HomeEvent.AddQuantity(item.id))
                            },
                            onRemoveQuantity = {
                                onEvent(HomeEvent.RemoveQuantity(item.id))
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeTopAppBar(
    uiState: HomeState,
    navController: NavController?
) {
    TopAppBar(
        title = {
            GreetingMessage(uiState = uiState)
        },
        actions = {
            MoreMenuButton(navController = navController)
        }
    )
}

@Composable
private fun GreetingMessage(uiState: HomeState) {
    Text(
        text = if (uiState.name == null) {
            stringResource(R.string.home_generic_greeting)
        } else {
            "${stringResource(R.string.home_personalized_greeting)}, ${uiState.name}"
        },
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
private fun MoreMenuButton(navController: NavController?) {
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
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
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

            Text(text = itemName)

            Spacer(modifier = Modifier.weight(1f))

            RemoveQuantityButton(
                imagePainter = painterResource(id = R.drawable.baseline_remove_circle_24),
                contentDescription = stringResource(R.string.remove),
                onClick = onRemoveQuantity
            )

            Text(text = quantity)

            AddQuantityButton(
                imageVector = Icons.Filled.AddCircle,
                contentDescription = stringResource(R.string.add),
                onClick = onAddQuantity
            )
        }
    }
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