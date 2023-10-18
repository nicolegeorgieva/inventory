package com.example.inventory.screen.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.inventory.ComposeViewModel
import com.example.inventory.data.repository.InventoryRepository
import com.example.inventory.data.repository.NameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val nameRepository: NameRepository,
    private val inventoryRepository: InventoryRepository
) : ComposeViewModel<HomeState, HomeEvent>() {
    private val name = mutableStateOf<String?>(null)
    private val inventoryList = mutableStateOf<ImmutableList<InventoryUi>?>(null)

    @Composable
    override fun uiState(): HomeState {
        LaunchedEffect(Unit) {
            name.value = nameRepository.getName()
            refreshInventoryList()
        }

        return HomeState(
            name = getName(),
            inventoryList = getInventoryList()
        )
    }

    @Composable
    private fun getName(): String? {
        return name.value
    }

    @Composable
    private fun getInventoryList(): ImmutableList<InventoryUi>? {
        return inventoryList.value
    }

    override fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.AddQuantity -> onAddQuantity(event.id)
            is HomeEvent.RemoveQuantity -> onRemoveQuantity(event.id)
        }
    }

    private fun onAddQuantity(id: String) {
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

    private fun onRemoveQuantity(id: String) {
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

    private suspend fun refreshInventoryList() {
        inventoryList.value = inventoryRepository.getAll().map {
            InventoryUi(
                id = it.id.toString(),
                name = it.name,
                quantity = it.quantity.toString(),
                imagePath = it.imagePath,
                category = it.category
            )
        }.toImmutableList()
    }
}