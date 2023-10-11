package com.example.inventory.data.repository

import com.example.inventory.data.datasource.NameDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NameRepository @Inject constructor(
    private val nameDataSource: NameDataSource
) {
    suspend fun getName(): String? {
        return withContext(Dispatchers.IO) {
            nameDataSource.getName()
        }
    }

    suspend fun setName(newName: String) {
        withContext(Dispatchers.IO) {
            nameDataSource.setName(newName.trim())
        }
    }

    suspend fun removeName() {
        withContext(Dispatchers.IO) {
            nameDataSource.removeName()
        }
    }
}