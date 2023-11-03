package com.example.inventory.screen.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewModelScope
import com.example.inventory.ComposeViewModel
import com.example.inventory.data.repository.inventory.InventoryRepository
import com.example.inventory.data.repository.name.NameRepository
import com.example.inventory.data.repository.quote.QuoteRepository
import com.example.inventory.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val nameRepository: NameRepository,
    private val quoteRepository: QuoteRepository,
    private val inventoryRepository: InventoryRepository,
    private val inventoryListProvider: InventoryListProvider,
    private val navigator: Navigator
) : ComposeViewModel<HomeState, HomeEvent>() {
    private val sortByAscending = mutableStateOf(true)
    private val categoryFilter = mutableStateOf("All")
    private val categoryFilterMenuExpanded = mutableStateOf(false)

    @Composable
    override fun uiState(): HomeState {
        return HomeState(
            name = getName(),
            quote = getQuote(),
            sortByAscending = getSortByAscending(),
            categoryFilter = getCategoryFilter(),
            categories = getCategories(),
            categoryFilterMenuExpanded = getCategoryFilterMenuExpandedState(),
            inventoryItemList = getInventoryItemList()
        )
    }

    @Composable
    private fun getName(): String? {
        return remember {
            nameRepository.getName()
        }.collectAsState(initial = null).value
    }

    @Composable
    private fun getQuote(): String? {
        return remember {
            quoteRepository.getQuote()
        }.collectAsState(initial = null).value
    }

    @Composable
    private fun getSortByAscending(): Boolean {
        return sortByAscending.value
    }

    @Composable
    private fun getCategoryFilter(): String {
        return categoryFilter.value
    }

    @Composable
    private fun getCategories(): ImmutableList<String> {
        return remember {
            inventoryRepository.getAll().map {
                it.mapNotNull { item ->
                    item.category
                }
            }
        }.collectAsState(initial = emptyList()).value.toSet().toImmutableList()
    }

    @Composable
    private fun getCategoryFilterMenuExpandedState(): Boolean {
        return categoryFilterMenuExpanded.value
    }

    @Composable
    private fun getInventoryItemList(): ImmutableList<InventoryItemType> {
        val sortByAscending = sortByAscending.value
        val filter = categoryFilter.value

        val items = remember(sortByAscending, filter) {
            when {
                filter == "All" && sortByAscending -> inventoryRepository.getAllOrderedByAscending()
                filter == "All" && !sortByAscending ->
                    inventoryRepository.getAllOrderedByDescending()

                filter != "All" && sortByAscending -> inventoryRepository.orderByAscending(filter)
                else -> inventoryRepository.orderByDescending(filter)
            }
        }.collectAsState(initial = null).value

        return inventoryListProvider.generateInventoryList(
            items ?: emptyList(),
            sortByAscending
        )
    }

    override fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnCategoryFilterMenuExpandedChange -> onCategoryFilterMenuExpandedChange(
                event.expanded
            )

            is HomeEvent.OnCategoryFilterOptionSelected -> onCategoryFilterOptionSelected(
                event.option
            )

            is HomeEvent.IncreaseQuantity -> onIncreaseQuantity(event.id)
            is HomeEvent.DecreaseQuantity -> onDecreaseQuantity(event.id)
            HomeEvent.OnAddButtonClicked -> onAddButtonClicked()
            is HomeEvent.OnSortOptionClicked -> onSortOptionClicked()
            is HomeEvent.OnItemClicked -> onItemClicked(event.id)
        }
    }

    private fun onSortOptionClicked() {
        sortByAscending.value = !sortByAscending.value
    }

    private fun onCategoryFilterMenuExpandedChange(expanded: Boolean) {
        categoryFilterMenuExpanded.value = expanded
    }

    private fun onCategoryFilterOptionSelected(option: String) {
        categoryFilter.value = option
    }

    private fun onIncreaseQuantity(id: String) {
        viewModelScope.launch {
            val inventoryItem = inventoryRepository.getById(UUID.fromString(id)).first()

            if (inventoryItem != null) {
                inventoryRepository.update(
                    inventoryItem.copy(
                        quantity = inventoryItem.quantity + 1
                    )
                )
            }
        }
    }

    private fun onDecreaseQuantity(id: String) {
        viewModelScope.launch {
            val inventoryItem = inventoryRepository.getById(UUID.fromString(id)).first()

            if (inventoryItem != null && inventoryItem.quantity > 0) {
                inventoryRepository.update(
                    inventoryItem.copy(
                        quantity = inventoryItem.quantity - 1
                    )
                )
            }
        }
    }

    private fun onAddButtonClicked() {
        categoryFilter.value = "All"

        viewModelScope.launch {
            navigator.navigate("addInventoryItem")
        }
    }

    private fun onItemClicked(id: String) {
        viewModelScope.launch {
            navigator.navigate("addInventoryItem?itemId=$id")
        }
    }
}