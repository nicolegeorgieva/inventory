package com.example.inventory.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.inventory.screen.home.component.AddButton
import com.example.inventory.screen.home.component.EmptyInventory
import com.example.inventory.screen.home.component.HomeTopAppBar
import com.example.inventory.screen.home.component.InventoryItemRow
import com.example.inventory.screen.home.component.SortFilterRow
import com.example.inventory.ui.theme.InventoryTheme

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
            AddButton(onAddButtonClicked = {
                onEvent(HomeEvent.OnAddButtonClicked)
            })
        },
        floatingActionButtonPosition = FabPosition.Center,
        content = { innerPadding ->
            LazyColumn(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = innerPadding
            ) {
                if (!uiState.inventoryItemList?.enoughItems.isNullOrEmpty() ||
                    !uiState.inventoryItemList?.toBuyItems.isNullOrEmpty()
                ) {
                    item(key = "sort filter row") {
                        SortFilterRow(
                            sortByAscending = uiState.sortByAscending,
                            onSortOptionClicked = {
                                onEvent(HomeEvent.OnSortOptionClicked(it))
                            },
                            category = uiState.categoryFilter,
                            menuExpanded = uiState.categoryFilterMenuExpanded,
                            onOptionSelected = {
                                onEvent(HomeEvent.OnCategoryFilterOptionSelected(it))
                            },
                            onExpandedChange = {
                                onEvent(HomeEvent.OnCategoryFilterMenuExpandedChange(it))
                            }
                        )
                    }

                    item(key = "to buy") {
                        ToBuySectionDivider(list = uiState.inventoryItemList)
                    }

                    if (uiState.inventoryItemList?.toBuyItems != null) {
                        items(uiState.inventoryItemList.toBuyItems) {
                            InventoryItemRow(
                                itemName = it.name,
                                quantity = it.quantity,
                                image = it.imagePath ?: "",
                                onAddQuantity = {
                                    onEvent(HomeEvent.IncreaseQuantity(it.id))
                                },
                                onRemoveQuantity = {
                                    onEvent(HomeEvent.DecreaseQuantity(it.id))
                                }
                            )
                        }
                    }

                    item(key = "enough") {
                        EnoughSectionDivider(list = uiState.inventoryItemList)
                    }

                    if (uiState.inventoryItemList?.enoughItems != null) {
                        items(uiState.inventoryItemList.enoughItems) {
                            InventoryItemRow(
                                itemName = it.name,
                                quantity = it.quantity,
                                image = it.imagePath ?: "",
                                onAddQuantity = {
                                    onEvent(HomeEvent.IncreaseQuantity(it.id))
                                },
                                onRemoveQuantity = {
                                    onEvent(HomeEvent.DecreaseQuantity(it.id))
                                }
                            )
                        }
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
private fun ToBuySectionDivider(list: InventoryItemList?) {
    Text(text = if (list?.toBuySection == SectionType.TOBUY) "To buy" else "")
}

@Composable
private fun EnoughSectionDivider(list: InventoryItemList?) {
    Text(text = if (list?.enoughSection == SectionType.ENOUGH) "Enough" else "")
}

@Preview(showBackground = true)
@Composable
private fun HomeGenericEmptyPreview() {
    InventoryTheme {
        HomeUi(
            navController = null,
            uiState = HomeState(
                name = null,
                sortByAscending = true,
                categoryFilter = "All",
                categoryFilterMenuExpanded = false,
                inventoryItemList = null
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
                sortByAscending = true,
                categoryFilter = "All",
                categoryFilterMenuExpanded = false,
                inventoryItemList = null
            ),
            onEvent = {}
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//private fun HomePersonalizedInventoryPreview() {
//    InventoryTheme {
//        HomeUi(
//            navController = null,
//            uiState = HomeState(
//                name = "Amy",
//                sortByAscending = true,
//                categoryFilter = "All",
//                categoryFilterMenuExpanded = false,
//                inventoryItemList = persistentListOf(
//                    InventoryItemUi(
//                        id = "",
//                        name = "Water bottles",
//                        quantity = "5",
//                        imagePath = null,
//                        category = null
//                    ),
//                    InventoryItemUi(
//                        id = "",
//                        name = "Kitchen roll",
//                        quantity = "4",
//                        imagePath = null,
//                        category = null
//                    ),
//                    InventoryItemUi(
//                        id = "",
//                        name = "Soap",
//                        quantity = "6",
//                        imagePath = null,
//                        category = null
//                    ),
//                    InventoryItemUi(
//                        id = "",
//                        name = "Shampoo",
//                        quantity = "2",
//                        imagePath = null,
//                        category = null
//                    ),
//                    InventoryItemUi(
//                        id = "",
//                        name = "Liquid laundry detergent",
//                        quantity = "1",
//                        imagePath = null,
//                        category = null
//                    ),
//                    InventoryItemUi(
//                        id = "",
//                        name = "Dishwasher tablets pack",
//                        quantity = "1",
//                        imagePath = null,
//                        category = null
//                    ),
//                    InventoryItemUi(
//                        id = "",
//                        name = "Shower gel",
//                        quantity = "3",
//                        imagePath = null,
//                        category = null
//                    ),
//                    InventoryItemUi(
//                        id = "",
//                        name = "Glass cleaner",
//                        quantity = "1",
//                        imagePath = null,
//                        category = null
//                    ),
//                    InventoryItemUi(
//                        id = "",
//                        name = "Floor cleaner",
//                        quantity = "1",
//                        imagePath = null,
//                        category = null
//                    ),
//                    InventoryItemUi(
//                        id = "",
//                        name = "Cotton buds pack",
//                        quantity = "3",
//                        imagePath = null,
//                        category = null
//                    )
//                )
//            ),
//            onEvent = {}
//        )
//    }
//}