package com.example.inventory.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
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
import com.example.inventory.screen.home.component.Section
import com.example.inventory.screen.home.component.SortFilterRow
import com.example.inventory.ui.theme.InventoryTheme
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

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
                if (!uiState.inventoryItemList.isNullOrEmpty()) {
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

                    items(uiState.inventoryItemList) {
                        when (it) {
                            is InventoryItemType.Item -> InventoryItemRow(
                                itemName = it.item.name,
                                quantity = it.item.quantity,
                                image = it.item.imagePath ?: "",
                                onAddQuantity = {
                                    onEvent(HomeEvent.IncreaseQuantity(it.item.id))
                                },
                                onRemoveQuantity = {
                                    onEvent(HomeEvent.DecreaseQuantity(it.item.id))
                                }
                            )

                            is InventoryItemType.Section -> Section(
                                section = it.section,
                                quantity = it.count
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

@Preview(showBackground = true)
@Composable
private fun HomePersonalizedInventoryPreview() {
    val toBuyList = persistentListOf(
        InventoryItemUi(
            id = "",
            name = "Water bottles",
            quantity = "5",
            imagePath = null,
            category = null
        ),
        InventoryItemUi(
            id = "",
            name = "Kitchen roll",
            quantity = "4",
            imagePath = null,
            category = null
        ),
        InventoryItemUi(
            id = "",
            name = "Soap",
            quantity = "6",
            imagePath = null,
            category = null
        )
    ).map {
        InventoryItemType.Item(
            item = it
        )
    }

    val enoughList = persistentListOf(
        InventoryItemUi(
            id = "",
            name = "Shampoo",
            quantity = "2",
            imagePath = null,
            category = null
        ),
        InventoryItemUi(
            id = "",
            name = "Liquid laundry detergent",
            quantity = "1",
            imagePath = null,
            category = null
        ),
        InventoryItemUi(
            id = "",
            name = "Dishwasher tablets pack",
            quantity = "1",
            imagePath = null,
            category = null
        ),
        InventoryItemUi(
            id = "",
            name = "Shower gel",
            quantity = "3",
            imagePath = null,
            category = null
        ),
        InventoryItemUi(
            id = "",
            name = "Glass cleaner",
            quantity = "1",
            imagePath = null,
            category = null
        ),
        InventoryItemUi(
            id = "",
            name = "Floor cleaner",
            quantity = "1",
            imagePath = null,
            category = null
        ),
        InventoryItemUi(
            id = "",
            name = "Cotton buds pack",
            quantity = "3",
            imagePath = null,
            category = null
        )
    ).map {
        InventoryItemType.Item(
            item = it
        )
    }

    val inventoryItemsList = buildList {
        add(InventoryItemType.Section(SectionType.TOBUY, toBuyList.size))
        addAll(toBuyList)
        add(InventoryItemType.Section(SectionType.ENOUGH, enoughList.size))
        addAll(enoughList)
    }.toImmutableList()

    InventoryTheme {
        HomeUi(
            navController = null,
            uiState = HomeState(
                name = "Amy",
                sortByAscending = true,
                categoryFilter = "All",
                categoryFilterMenuExpanded = false,
                inventoryItemList = inventoryItemsList
            ),
            onEvent = {}
        )
    }
}