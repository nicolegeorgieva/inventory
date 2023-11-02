package com.example.inventory.data.repository.name

import kotlinx.coroutines.flow.Flow

interface NameRepository {
    fun getName(): Flow<String?>
    suspend fun setName(newName: String)
    suspend fun removeName()
}