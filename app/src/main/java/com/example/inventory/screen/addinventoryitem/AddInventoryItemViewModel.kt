package com.example.inventory.screen.addinventoryitem

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.inventory.ComposeViewModel
import com.example.inventory.data.model.InventoryItem
import com.example.inventory.data.repository.InventoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AddInventoryItemViewModel @Inject constructor(
    private val inventoryRepository: InventoryRepository
) :
    ComposeViewModel<AddInventoryItemState, AddInventoryItemEvent>() {

    private val name = mutableStateOf<String?>(null)
    private val quantity = mutableStateOf<Int?>(null)
    private val minQuantityTarget = mutableStateOf<Int?>(null)
    private val category = mutableStateOf<String?>(null)
    private val description = mutableStateOf<String?>(null)
    private val image = mutableStateOf<String?>(null)

    @Composable
    override fun uiState(): AddInventoryItemState {
        return AddInventoryItemState(
            name = getName(),
            quantity = getQuantity(),
            minQuantityTarget = getMinQuantityTarget(),
            category = getCategory(),
            description = getDescription(),
            image = getImage()
        )
    }

    @Composable
    private fun getName(): String? {
        return name.value
    }

    @Composable
    private fun getQuantity(): Int? {
        return quantity.value
    }

    @Composable
    private fun getMinQuantityTarget(): Int? {
        return minQuantityTarget.value
    }

    @Composable
    private fun getCategory(): String? {
        return category.value
    }

    @Composable
    private fun getDescription(): String? {
        return description.value
    }

    @Composable
    private fun getImage(): String? {
        return image.value
    }

    override fun onEvent(event: AddInventoryItemEvent) {
        when (event) {
            is AddInventoryItemEvent.SetCategory -> setCategory(event.newCategory)
            is AddInventoryItemEvent.SetDescription -> setDescription(event.newDescription)
            is AddInventoryItemEvent.SetImage -> setImage(event.newImage)
            is AddInventoryItemEvent.SetMinQuantityTarget -> setMinQuantityTarget(
                event.newMinQuantityTarget
            )

            is AddInventoryItemEvent.SetName -> setName(event.newName)
            is AddInventoryItemEvent.SetQuantity -> setQuantity(event.newQuantity)
            AddInventoryItemEvent.AddInventoryItem -> addInventoryItem()
        }
    }

    private fun setCategory(newCategory: String) {
        category.value = newCategory
    }

    private fun setDescription(newDescription: String) {
        description.value = newDescription
    }

    private fun setImage(newImage: String) {
        image.value = newImage
    }

    private fun setMinQuantityTarget(newMinQuantityTarget: Int) {
        minQuantityTarget.value = newMinQuantityTarget
    }

    private fun setName(newName: String) {
        name.value = newName
    }

    private fun setQuantity(newQuantity: Int) {
        quantity.value = newQuantity
    }

    private fun addInventoryItem() {
        viewModelScope.launch {
            inventoryRepository.save(
                InventoryItem(
                    id = UUID.randomUUID(),
                    name = getName(),
                    quantity = getQuantity(),
                    minQuantityTarget = getMinQuantityTarget(),
                    category = getCategory(),
                    description = getDescription(),
                    imageUrl = getImage()
                )
            )
        }
    }
}