package com.example.inventory.data.repository.name

import com.example.inventory.data.datasource.name.NameDataSource
import com.example.inventory.dispatcher.DispatcherProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NameRepositoryImpl @Inject constructor(
    private val nameDataSource: NameDataSource,
    private val dispatchers: DispatcherProvider
) : NameRepository {
    override fun getName(): Flow<String?> {
        return nameDataSource.getName().flowOn(dispatchers.io)
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