package com.example.inventory.screen.addinventoryitem

import android.net.Uri

sealed interface AddInventoryItemEvent {
    data class SetName(val newName: String) : AddInventoryItemEvent
    data class SetQuantity(val newQuantity: String) : AddInventoryItemEvent
    data class SetMinQuantityTarget(val newMinQuantityTarget: String) : AddInventoryItemEvent
    data class SetCategory(val newCategory: String) : AddInventoryItemEvent
    data class SetDescription(val newDescription: String) : AddInventoryItemEvent
    data class SetImage(val newImage: Uri?) : AddInventoryItemEvent
    data class OnLinkValueChange(val link: String?) : AddInventoryItemEvent
    data object AddInventoryItem : AddInventoryItemEvent
}