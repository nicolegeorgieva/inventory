package com.example.inventory.screen.addinventoryitem

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.inventory.ComposeViewModel
import com.example.inventory.IdProvider
import com.example.inventory.Navigator
import com.example.inventory.data.model.InventoryItem
import com.example.inventory.data.repository.InventoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddInventoryItemViewModel @Inject constructor(
    private val inventoryRepository: InventoryRepository,
    private val idProvider: IdProvider,
    private val navigator: Navigator
) :
    ComposeViewModel<AddInventoryItemState, AddInventoryItemEvent>() {

    private val name = mutableStateOf<String?>(null)
    private val quantity = mutableStateOf<String?>(null)
    private val minQuantityTarget = mutableStateOf<String?>(null)
    private val category = mutableStateOf<String?>(null)
    private val description = mutableStateOf<String?>(null)
    private val link = mutableStateOf<String?>(null)
    private val imagePath = mutableStateOf<String?>(null)
    private val addWithoutRequired = mutableStateOf(false)

    @Composable
    override fun uiState(): AddInventoryItemState {
        return AddInventoryItemState(
            name = getName(),
            quantity = getQuantity(),
            minQuantityTarget = getMinQuantityTarget(),
            category = getCategory(),
            description = getDescription(),
            link = getLink(),
            imagePath = getImagePath(),
            addWithoutRequired = getAddWithoutRequiredState()
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
    private fun getLink(): String? {
        return link.value
    }

    @Composable
    private fun getImagePath(): String? {
        return imagePath.value
    }

    @Composable
    private fun getAddWithoutRequiredState(): Boolean {
        return addWithoutRequired.value
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
            is AddInventoryItemEvent.SetLinkImage -> setLinkImage(event.newImage)
        }
    }

    private fun setCategory(newCategory: String) {
        category.value = newCategory
    }

    private fun setDescription(newDescription: String) {
        description.value = newDescription
    }

    private fun setFileImage(newImage: Uri?) {
        imagePath.value = newImage?.toString()
    }

    private fun setLinkImage(newImage: String?) {
        imagePath.value = newImage
        link.value = null
    }

    private fun setName(newName: String) {
        name.value = newName

        val validateInput = validateAddItemInput()

        addWithoutRequired.value = !validateInput
    }

    private fun setQuantity(newQuantity: String) {
        quantity.value = newQuantity

        val validateInput = validateAddItemInput()

        addWithoutRequired.value = !validateInput
    }

    private fun setMinQuantityTarget(newMinQuantityTarget: String) {
        minQuantityTarget.value = newMinQuantityTarget

        val validateInput = validateAddItemInput()

        addWithoutRequired.value = !validateInput
    }

    private fun onLinkValueChange(linkValue: String?) {
        link.value = linkValue
    }

    private fun addInventoryItem() {
        val validateInput = validateAddItemInput()

        if (validateInput) {
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

                navigator.back()
            }
        } else {
            addWithoutRequired.value = true
        }
    }

    private fun validateAddItemInput(): Boolean {
        return !name.value.isNullOrBlank() &&
                quantity.value.isValidIntNumber() &&
                minQuantityTarget.value.isValidIntNumber()
    }

    private fun String?.isValidIntNumber(): Boolean {
        return this?.toIntOrNull() != null && this.isNotBlank()
    }
}