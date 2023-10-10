package com.example.inventory.data.repository

import com.example.inventory.data.datasource.InventoryDataSource
import javax.inject.Inject

class InventoryRepository @Inject constructor(
    private val inventoryDataSource: InventoryDataSource
) {

}