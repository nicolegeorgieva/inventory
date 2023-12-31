package com.example.inventory.screen.addoreditinventoryitem

sealed interface AddEditInventoryItemEvent {
    data class SetName(val newName: String) : AddEditInventoryItemEvent
    data class SetQuantity(val newQuantity: String) : AddEditInventoryItemEvent
    data class SetMinQuantityTarget(val newMinQuantityTarget: String) : AddEditInventoryItemEvent
    data class SetCategory(val newCategory: String?) : AddEditInventoryItemEvent
    data object OnExpandedChange : AddEditInventoryItemEvent
    data object OnOpenCategoryDialog : AddEditInventoryItemEvent
    data class OnNewCategoryValueChange(val newCategoryValue: String) : AddEditInventoryItemEvent
    data object OnCloseAddCategoryDialog : AddEditInventoryItemEvent
    data class SetDescription(val newDescription: String) : AddEditInventoryItemEvent
    data class SetLinkImage(val newImage: String?) : AddEditInventoryItemEvent
    data class OnLinkValueChange(val link: String?) : AddEditInventoryItemEvent
    data object AddInventoryItem : AddEditInventoryItemEvent
    data class LoadItem(val id: String) : AddEditInventoryItemEvent
    data object DeleteButtonPressed : AddEditInventoryItemEvent
    data object OnCloseDeleteItemDialog : AddEditInventoryItemEvent
    data object OnConfirmDeleting : AddEditInventoryItemEvent
}