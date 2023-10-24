package com.example.inventory.screen.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.inventory.ComposeViewModel
import com.example.inventory.data.model.InventoryItem
import com.example.inventory.data.repository.inventory.InventoryRepository
import com.example.inventory.data.repository.name.NameRepository
import com.example.inventory.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val nameRepository: NameRepository,
    private val inventoryRepository: InventoryRepository,
    private val sectionDivisionProvider: SectionDivisionProvider,
    private val navigator: Navigator
) : ComposeViewModel<HomeState, HomeEvent>() {
    private val name = mutableStateOf<String?>(null)
    private val sortByAscending = mutableStateOf(true)
    private val categoryFilter = mutableStateOf("All")
    private val categoryFilterMenuExpanded = mutableStateOf(false)
    private val inventoryItemList = mutableStateOf<ImmutableList<InventoryItemType>?>(null)

    @Composable
    override fun uiState(): HomeState {
        LaunchedEffect(Unit) {
            name.value = nameRepository.getName()
            refreshInventoryList()
        }

        return HomeState(
            name = getName(),
            sortByAscending = getSortByAscending(),
            categoryFilter = getCategoryFilter(),
            categoryFilterMenuExpanded = getCategoryFilterMenuExpandedState(),
            inventoryItemList = getInventoryItemList()
        )
    }

    @Composable
    private fun getName(): String? {
        return name.value
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
            is HomeEvent.OnSortOptionClicked -> onSortOptionClicked(event.sortByAscending)
        }
    }

    private fun onSortOptionClicked(sortByAscendingValue: Boolean) {
        sortByAscending.value = sortByAscendingValue
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
                generateInventoryList(items)
            }
        } else {
            viewModelScope.launch {
                items = inventoryRepository.getAll()
                generateInventoryList(items)
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

            if (inventoryItem != null) {
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

    private suspend fun refreshInventoryList() {
        val items = if (categoryFilter.value == "All") {
            inventoryRepository.getAll()
        } else {
            inventoryRepository.getAllByCategory(categoryFilter.value)
        }

        generateInventoryList(items)
    }

    private fun generateInventoryList(items: List<InventoryItem>) {
        val toBuy = sectionDivisionProvider.provideItemsBySection(
            items, SectionType.TOBUY
        ).map {
            InventoryItemType.Item(
                item = it
            )
        }

        val enough = sectionDivisionProvider.provideItemsBySection(
            items, SectionType.ENOUGH
        ).map {
            InventoryItemType.Item(
                item = it
            )
        }

        inventoryItemList.value = buildList {
            add(InventoryItemType.Section(SectionType.TOBUY))
            addAll(toBuy)
            add(InventoryItemType.Section(SectionType.ENOUGH))
            addAll(enough)
        }.toImmutableList()
    }
}