package com.example.inventory.screen.addinventoryitem

import android.net.Uri

sealed interface AddInventoryItemEvent {
    data class SetName(val newName: String) : AddInventoryItemEvent
    data class SetQuantity(val newQuantity: String) : AddInventoryItemEvent
    data class SetMinQuantityTarget(val newMinQuantityTarget: String) : AddInventoryItemEvent
    data class SetCategory(val newCategory: String) : AddInventoryItemEvent
    data class SetDescription(val newDescription: String) : AddInventoryItemEvent
    data class OnTabChange(val selectedTabIndex: Int) : AddInventoryItemEvent
    data class SetFileImage(val newImage: Uri?) : AddInventoryItemEvent
    data class SetLinkImage(val newImage: String?) : AddInventoryItemEvent
    data class OnLinkValueChange(val link: String?) : AddInventoryItemEvent
    data object AddInventoryItem : AddInventoryItemEvent
}