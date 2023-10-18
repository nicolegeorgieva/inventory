package com.example.inventory.data.repository.name

import com.example.inventory.data.datasource.NameDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NameRepositoryImpl @Inject constructor(
    private val nameDataSource: NameDataSource
) : NameRepository {
    override suspend fun getName(): String? {
        return withContext(Dispatchers.IO) {
            nameDataSource.getName()
        }
    }

    override suspend fun setName(newName: String) {
        if (newName.isBlank()) return

        withContext(Dispatchers.IO) {
            nameDataSource.setName(newName.trim())
        }
    }

    override suspend fun removeName() {
        withContext(Dispatchers.IO) {
            nameDataSource.removeName()
        }
    }
}