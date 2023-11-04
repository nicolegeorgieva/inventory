package com.example.inventory.screen.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.inventory.ComposeViewModel
import com.example.inventory.data.model.InventoryItem
import com.example.inventory.data.repository.inventory.InventoryRepository
import com.example.inventory.data.repository.name.NameRepository
import com.example.inventory.data.repository.quote.QuoteRepository
import com.example.inventory.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.collections.immutable.toPersistentSet
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
    private val name = mutableStateOf<String?>(null)
    private val quote = mutableStateOf<String?>(null)
    private val sortByAscending = mutableStateOf(true)
    private val categoryFilter = mutableStateOf("All")
    private val categories = mutableStateOf(persistentListOf<String>())
    private val categoryFilterMenuExpanded = mutableStateOf(false)
    private val inventoryItemList = mutableStateOf<ImmutableList<InventoryItemType>?>(null)

    @Composable
    override fun uiState(): HomeState {
        LaunchedEffect(Unit) {
            name.value = nameRepository.getName()
            quote.value = quoteRepository.getLocalOrDefaultQuote()
            quote.value = quoteRepository.getQuoteWithRemoteCall()

            refreshInventoryList()
        }

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
        return name.value
    }

    @Composable
    private fun getQuote(): String? {
        return quote.value
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
        return categories.value
    }

    @Composable
    private fun getCategoryFilterMenuExpandedState(): Boolean {
        return categoryFilterMenuExpanded.value
    }

    @Composable
    private fun getInventoryItemList(): ImmutableList<InventoryItemType>? {
        return inventoryItemList.value
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

        viewModelScope.launch {
            refreshInventoryList()
        }
    }

    private fun onCategoryFilterMenuExpandedChange(expanded: Boolean) {
        categoryFilterMenuExpanded.value = expanded
    }

    private fun onCategoryFilterOptionSelected(option: String) {
        categoryFilter.value = option
        var items: List<InventoryItem>

        if (option != "All") {
            viewModelScope.launch {
                items = inventoryRepository.getAllByCategory(option)
                inventoryItemList.value = inventoryListProvider.generateInventoryList(
                    items,
                    sortByAscending.value
                )
            }
        } else {
            viewModelScope.launch {
                items = inventoryRepository.getAllOrderedByAscending()
                inventoryItemList.value = inventoryListProvider.generateInventoryList(
                    items,
                    sortByAscending.value
                )
            }
        }
    }

    private fun onIncreaseQuantity(id: String) {
        viewModelScope.launch {
            val inventoryItem = inventoryRepository.getById(UUID.fromString(id))

            if (inventoryItem != null) {
                inventoryRepository.update(
                    inventoryItem.copy(
                        quantity = inventoryItem.quantity + 1
                    )
                )

                refreshInventoryList()
            }
        }
    }

    private fun onDecreaseQuantity(id: String) {
        viewModelScope.launch {
            val inventoryItem = inventoryRepository.getById(UUID.fromString(id))

            if (inventoryItem != null && inventoryItem.quantity > 0) {
                inventoryRepository.update(
                    inventoryItem.copy(
                        quantity = inventoryItem.quantity - 1
                    )
                )

                refreshInventoryList()
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

    private suspend fun refreshInventoryList() {
        val items = if (categoryFilter.value == "All") {
            if (sortByAscending.value) {
                inventoryRepository.getAllOrderedByAscending()
            } else {
                inventoryRepository.getAllOrderedByDescending()
            }
        } else {
            if (sortByAscending.value) {
                inventoryRepository.orderByAscending(categoryFilter.value)
            } else {
                inventoryRepository.orderByDescending(categoryFilter.value)
            }
        }

        inventoryItemList.value = inventoryListProvider.generateInventoryList(
            items,
            sortByAscending.value
        )

        val categories = mutableSetOf<String>()

        inventoryRepository.getAll().forEach {
            if (it.category != null) categories.add(it.category)
        }

        this.categories.value = categories.toPersistentSet().sorted().toPersistentList()
    }
}