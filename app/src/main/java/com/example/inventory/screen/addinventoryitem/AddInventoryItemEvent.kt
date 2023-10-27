package com.example.inventory.screen.addinventoryitem

sealed interface AddInventoryItemEvent {
    data class SetName(val newName: String) : AddInventoryItemEvent
    data class SetQuantity(val newQuantity: String) : AddInventoryItemEvent
    data class SetMinQuantityTarget(val newMinQuantityTarget: String) : AddInventoryItemEvent
    data class SetCategory(val newCategory: String) : AddInventoryItemEvent
    data object OnExpandedChange : AddInventoryItemEvent
    data object OnOpenCategoryDialog : AddInventoryItemEvent
    data class OnNewCategoryValueChange(val newCategoryValue: String) : AddInventoryItemEvent
    data object OnCloseDialog : AddInventoryItemEvent
    data class SetDescription(val newDescription: String) : AddInventoryItemEvent
    data class SetLinkImage(val newImage: String?) : AddInventoryItemEvent
    data class OnLinkValueChange(val link: String?) : AddInventoryItemEvent
    data object AddInventoryItem : AddInventoryItemEvent
}