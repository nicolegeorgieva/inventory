package com.example.inventory.screen.addoreditinventoryitem

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.inventory.ComposeViewModel
import com.example.inventory.data.model.InventoryItem
import com.example.inventory.data.repository.inventory.InventoryRepository
import com.example.inventory.domain.IdProvider
import com.example.inventory.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AddEditInventoryItemViewModel @Inject constructor(
    private val inventoryRepository: InventoryRepository,
    private val idProvider: IdProvider,
    private val navigator: Navigator
) :
    ComposeViewModel<AddEditInventoryItemState, AddEditInventoryItemEvent>() {

    private val name = mutableStateOf<String?>(null)
    private val quantity = mutableStateOf<String?>(null)
    private val minQuantityTarget = mutableStateOf<String?>(null)
    private val category = mutableStateOf<String?>(null)
    private val categories = mutableStateOf<ImmutableList<String>>(persistentListOf())
    private val categoryMenuExpanded = mutableStateOf(false)
    private val description = mutableStateOf<String?>(null)
    private val link = mutableStateOf<String?>(null)
    private val imagePath = mutableStateOf<String?>(null)
    private val newCategoryValue = mutableStateOf<String?>(null)
    private val openAddCategoryDialog = mutableStateOf(false)
    private val validName = mutableStateOf(true)
    private val validQuantity = mutableStateOf(true)
    private val validMinQuantityTarget = mutableStateOf(true)
    private val showDeleteItemDialog = mutableStateOf(false)
    private var existingId: String? = null

    @Composable
    override fun uiState(): AddEditInventoryItemState {
        return AddEditInventoryItemState(
            name = getName(),
            quantity = getQuantity(),
            minQuantityTarget = getMinQuantityTarget(),
            category = getCategory(),
            categories = getCategories(),
            expanded = getCategoryMenuExpanded(),
            description = getDescription(),
            link = getLink(),
            imagePath = getImagePath(),
            newCategoryValue = getNewCategoryValue(),
            openAddCategoryDialog = getOpenAddCategoryDialog(),
            validName = getValidNameState(),
            validQuantity = getValidQuantityState(),
            validMinQuantityTarget = getValidMinQuantityTargetState(),
            showDeleteItemDialog = getShowDeleteItemDialogState()
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
    private fun getCategories(): ImmutableList<String> {
        return categories.value
    }

    @Composable
    private fun getCategoryMenuExpanded(): Boolean {
        return categoryMenuExpanded.value
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
    private fun getNewCategoryValue(): String? {
        return newCategoryValue.value
    }

    @Composable
    private fun getOpenAddCategoryDialog(): Boolean {
        return openAddCategoryDialog.value
    }

    @Composable
    private fun getValidNameState(): Boolean {
        return validName.value
    }

    @Composable
    private fun getValidQuantityState(): Boolean {
        return validQuantity.value
    }

    @Composable
    private fun getValidMinQuantityTargetState(): Boolean {
        return validMinQuantityTarget.value
    }

    @Composable
    private fun getShowDeleteItemDialogState(): Boolean {
        return showDeleteItemDialog.value
    }

    override fun onEvent(event: AddEditInventoryItemEvent) {
        when (event) {
            is AddEditInventoryItemEvent.SetCategory -> setCategory(event.newCategory)
            is AddEditInventoryItemEvent.SetDescription -> setDescription(event.newDescription)
            is AddEditInventoryItemEvent.SetMinQuantityTarget -> setMinQuantityTarget(
                event.newMinQuantityTarget
            )

            is AddEditInventoryItemEvent.SetName -> setName(event.newName)
            is AddEditInventoryItemEvent.SetQuantity -> setQuantity(event.newQuantity)
            AddEditInventoryItemEvent.AddInventoryItem -> addEditInventoryItem()
            is AddEditInventoryItemEvent.OnLinkValueChange -> onLinkValueChange(event.link)
            is AddEditInventoryItemEvent.SetLinkImage -> setLinkImage(event.newImage)
            AddEditInventoryItemEvent.OnExpandedChange -> onExpandedChange()
            AddEditInventoryItemEvent.OnCloseAddCategoryDialog -> onShowDialogChange()
            is AddEditInventoryItemEvent.OnNewCategoryValueChange -> onNewCategoryValueChange(
                event.newCategoryValue
            )

            AddEditInventoryItemEvent.OnOpenCategoryDialog -> onShowDialogChange()
            is AddEditInventoryItemEvent.LoadItem -> loadItem(event.id)
            AddEditInventoryItemEvent.DeleteButtonPressed -> onDeleteButtonPressed()
            AddEditInventoryItemEvent.OnCloseDeleteItemDialog -> onCloseDeleteItemDialog()
            AddEditInventoryItemEvent.OnConfirmDeleting -> deleteItem()
        }
    }

    private fun setCategory(newCategory: String?) {
        category.value = newCategory
        if (newCategory == null) return
        categories.value = (categories.value + newCategory)
            .toSet()
            .toImmutableList()
        openAddCategoryDialog.value = false
    }

    private fun onExpandedChange() {
        viewModelScope.launch {
            val savedCategories = inventoryRepository.getAll().first().mapNotNull {
                it.category
            }

            categories.value = (categories.value + savedCategories).toSet().toImmutableList()
        }

        categoryMenuExpanded.value = !categoryMenuExpanded.value
    }

    private fun setDescription(newDescription: String) {
        description.value = newDescription
    }

    private fun setLinkImage(newImage: String?) {
        imagePath.value = newImage
        link.value = null
    }

    private fun setName(newName: String) {
        name.value = newName

        validName.value = validateName()
    }

    private fun setQuantity(newQuantity: String) {
        quantity.value = newQuantity

        validQuantity.value = validateQuantity()
    }

    private fun setMinQuantityTarget(newMinQuantityTarget: String) {
        minQuantityTarget.value = newMinQuantityTarget

        validMinQuantityTarget.value = validateMinQuantityTarget()
    }

    private fun onLinkValueChange(linkValue: String?) {
        link.value = linkValue
    }

    private fun onShowDialogChange() {
        openAddCategoryDialog.value = !openAddCategoryDialog.value
        categoryMenuExpanded.value = false
    }

    private fun onNewCategoryValueChange(value: String) {
        newCategoryValue.value = value
    }

    private fun loadItem(id: String) {
        viewModelScope.launch {
            val item = inventoryRepository.getById(UUID.fromString(id)).first()

            name.value = item?.name ?: ""
            quantity.value = item?.quantity.toString()
            minQuantityTarget.value = item?.minQuantityTarget.toString()
            category.value = item?.category
            description.value = item?.description
            imagePath.value = item?.imagePath
        }

        existingId = id
    }

    private fun onDeleteButtonPressed() {
        showDeleteItemDialog.value = true
    }

    private fun deleteItem() {
        viewModelScope.launch {
            val item = inventoryRepository.getById(UUID.fromString(existingId)).first()

            if (item != null) {
                inventoryRepository.delete(item)
            }

            navigator.back()
        }
    }

    private fun onCloseDeleteItemDialog() {
        showDeleteItemDialog.value = false
    }

    private fun addEditInventoryItem() {
        val validateName = validateName()
        val validateQuantity = validateQuantity()
        val validateMinQuantityTarget = validateMinQuantityTarget()

        if (!validateName) {
            validName.value = false
        }

        if (!validateQuantity) {
            validQuantity.value = false
        }

        if (!validateMinQuantityTarget) {
            validMinQuantityTarget.value = false
        }

        if (!validateName || !validateQuantity || !validateMinQuantityTarget) {
            return
        }

        viewModelScope.launch {
            val checkExistingName = inventoryRepository.getAll().first().filter {
                it.name == name.value
            }

            val inventoryItem = InventoryItem(
                id = if (existingId == null) idProvider.generateId() else UUID.fromString(
                    existingId
                ),
                name = name.value ?: "",
                quantity = quantity.value?.toIntOrNull() ?: 0,
                minQuantityTarget = minQuantityTarget.value?.toIntOrNull() ?: 0,
                category = category.value,
                description = description.value ?: "",
                imagePath = imagePath.value ?: ""
            )

            if (checkExistingName.isNotEmpty()) {
                inventoryRepository.update(inventoryItem)
            } else {
                inventoryRepository.add(inventoryItem)
            }

            navigator.back()
        }
    }

    private fun validateName(): Boolean {
        return !name.value.isNullOrBlank()
    }

    private fun validateQuantity(): Boolean {
        return quantity.value.isValidIntNumber()
    }

    private fun validateMinQuantityTarget(): Boolean {
        return minQuantityTarget.value.isValidIntNumber()
    }

    private fun String?.isValidIntNumber(): Boolean {
        return this?.toIntOrNull() != null && this.isNotBlank()
    }
}