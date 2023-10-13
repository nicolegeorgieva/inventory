package com.example.inventory.screen.addinventoryitem

import android.net.Uri
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
    private val quantity = mutableStateOf<String?>(null)
    private val minQuantityTarget = mutableStateOf<String?>(null)
    private val category = mutableStateOf<String?>(null)
    private val description = mutableStateOf<String?>(null)
    private val imagePath = mutableStateOf<String?>(null)

    @Composable
    override fun uiState(): AddInventoryItemState {
        return AddInventoryItemState(
            name = getName(),
            quantity = getQuantity(),
            minQuantityTarget = getMinQuantityTarget(),
            category = getCategory(),
            description = getDescription(),
            imagePath = getImagePath()
        )
    }

    @Composable
    private fun getName(): String? {
        return name.value
    }

    @Composable
    private fun getQuantity(): String? {
        return quantity.value
    }

    @Composable
    private fun getMinQuantityTarget(): String? {
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
    private fun getImagePath(): String? {
        return imagePath.value
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

    private fun setImage(newImage: Uri?) {
        imagePath.value = newImage?.toString()
    }

    private fun setMinQuantityTarget(newMinQuantityTarget: String) {
        minQuantityTarget.value = newMinQuantityTarget
    }

    private fun setName(newName: String) {
        name.value = newName
    }

    private fun setQuantity(newQuantity: String) {
        quantity.value = newQuantity
    }

    private fun addInventoryItem() {
        if (name.value != null && quantity.value != null && minQuantityTarget.value != null) {
            viewModelScope.launch {
                inventoryRepository.save(
                    InventoryItem(
                        id = UUID.randomUUID(),
                        name = name.value ?: "",
                        quantity = quantity.value?.toIntOrNull() ?: 0,
                        minQuantityTarget = minQuantityTarget.value?.toIntOrNull() ?: 0,
                        category = category.value ?: "",
                        description = description.value ?: "",
                        imagePath = imagePath.value ?: ""
                    )
                )
            }
        }
    }
}