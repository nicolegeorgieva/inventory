package com.example.inventory.screen.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.inventory.ComposeViewModel
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
    private val navigator: Navigator
) : ComposeViewModel<HomeState, HomeEvent>() {
    private val name = mutableStateOf<String?>(null)
    private val categoryFilter = mutableStateOf("All")
    private val categoryFilterMenuExpanded = mutableStateOf(false)
    private val inventoryList = mutableStateOf<ImmutableList<InventoryItemUi>?>(null)

    @Composable
    override fun uiState(): HomeState {
        LaunchedEffect(Unit) {
            name.value = nameRepository.getName()
            refreshInventoryList()
        }

        return HomeState(
            name = getName(),
            categoryFilter = getCategoryFilter(),
            categoryFilterMenuExpanded = getCategoryFilterMenuExpandedState(),
            inventoryList = getInventoryList()
        )
    }

    @Composable
    private fun getName(): String? {
        return name.value
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
    private fun getInventoryList(): ImmutableList<InventoryItemUi>? {
        return inventoryList.value
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
        }
    }

    private fun onCategoryFilterMenuExpandedChange(expanded: Boolean) {
        categoryFilterMenuExpanded.value = expanded
    }

    private fun onCategoryFilterOptionSelected(option: String) {
        categoryFilter.value = option

        if (option != "All") {
            viewModelScope.launch {
                inventoryList.value = inventoryRepository.getAllByCategory(option).map {
                    InventoryItemUi(
                        id = it.id.toString(),
                        name = it.name,
                        quantity = it.quantity.toString(),
                        imagePath = it.imagePath,
                        category = it.category
                    )
                }.toImmutableList()
            }
        } else {
            viewModelScope.launch {
                inventoryList.value = inventoryRepository.getAll().map {
                    InventoryItemUi(
                        id = it.id.toString(),
                        name = it.name,
                        quantity = it.quantity.toString(),
                        imagePath = it.imagePath,
                        category = it.category
                    )
                }.toImmutableList()
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
        inventoryList.value = inventoryRepository.getAll().map {
            InventoryItemUi(
                id = it.id.toString(),
                name = it.name,
                quantity = it.quantity.toString(),
                imagePath = it.imagePath,
                category = it.category
            )
        }.toImmutableList()
    }
}