package com.example.inventory.data.repository.name

import com.example.inventory.data.datasource.NameDataSource
import com.example.inventory.dispatcher.DispatcherProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NameRepositoryImpl @Inject constructor(
    private val nameDataSource: NameDataSource,
    private val dispatchers: DispatcherProvider
) : NameRepository {
    override suspend fun getName(): String? {
        return withContext(dispatchers.io) {
            nameDataSource.getName()
        }
    }

    override suspend fun setName(newName: String) {
        if (newName.isBlank()) return

        withContext(dispatchers.io) {
            nameDataSource.setName(newName.trim())
        }
    }

    override suspend fun removeName() {
        withContext(dispatchers.io) {
            nameDataSource.removeName()
        }
    }
}