package com.example.inventory.screen.addinventoryitem

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.inventory.ComposeViewModel
import com.example.inventory.IdProvider
import com.example.inventory.data.model.InventoryItem
import com.example.inventory.data.repository.InventoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddInventoryItemViewModel @Inject constructor(
    private val inventoryRepository: InventoryRepository,
    private val idProvider: IdProvider
) :
    ComposeViewModel<AddInventoryItemState, AddInventoryItemEvent>() {

    private val name = mutableStateOf<String?>(null)
    private val quantity = mutableStateOf<String?>(null)
    private val minQuantityTarget = mutableStateOf<String?>(null)
    private val category = mutableStateOf<String?>(null)
    private val description = mutableStateOf<String?>(null)
    private val link = mutableStateOf<String?>(null)
    private val imagePath = mutableStateOf<String?>(null)
    private val tabs = mutableStateOf(listOf("From files", "From link"))
    private val selectedTabIndex = mutableIntStateOf(0)

    @Composable
    override fun uiState(): AddInventoryItemState {
        return AddInventoryItemState(
            name = getName(),
            quantity = getQuantity(),
            minQuantityTarget = getMinQuantityTarget(),
            category = getCategory(),
            description = getDescription(),
            tabs = getTabs(),
            selectedTabIndex = getSelectedTabIndex(),
            link = getLink(),
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
    private fun getTabs(): List<String> {
        return tabs.value
    }

    @Composable
    private fun getSelectedTabIndex(): Int {
        return selectedTabIndex.intValue
    }

    @Composable
    private fun getLink(): String? {
        return link.value
    }

    @Composable
    private fun getImagePath(): String? {
        return imagePath.value
    }

    override fun onEvent(event: AddInventoryItemEvent) {
        when (event) {
            is AddInventoryItemEvent.SetCategory -> setCategory(event.newCategory)
            is AddInventoryItemEvent.SetDescription -> setDescription(event.newDescription)
            is AddInventoryItemEvent.SetFileImage -> setFileImage(event.newImage)
            is AddInventoryItemEvent.SetMinQuantityTarget -> setMinQuantityTarget(
                event.newMinQuantityTarget
            )

            is AddInventoryItemEvent.SetName -> setName(event.newName)
            is AddInventoryItemEvent.SetQuantity -> setQuantity(event.newQuantity)
            AddInventoryItemEvent.AddInventoryItem -> addInventoryItem()
            is AddInventoryItemEvent.OnLinkValueChange -> onLinkValueChange(event.link)

            is AddInventoryItemEvent.OnTabChange -> onTabChange(event.selectedTabIndex)
            is AddInventoryItemEvent.SetLinkImage -> setLinkImage(event.newImage)
        }
    }

    private fun setCategory(newCategory: String) {
        category.value = newCategory
    }

    private fun setDescription(newDescription: String) {
        description.value = newDescription
    }

    private fun onTabChange(index: Int) {
        selectedTabIndex.intValue = index
    }

    private fun setFileImage(newImage: Uri?) {
        imagePath.value = newImage?.toString()
    }

    private fun setLinkImage(newImage: String?) {
        imagePath.value = newImage
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

    private fun onLinkValueChange(linkValue: String?) {
        link.value = linkValue
    }

    private fun addInventoryItem() {
        if (name.value != null && quantity.value != null && minQuantityTarget.value != null) {
            viewModelScope.launch {
                inventoryRepository.save(
                    InventoryItem(
                        id = idProvider.generateId(),
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