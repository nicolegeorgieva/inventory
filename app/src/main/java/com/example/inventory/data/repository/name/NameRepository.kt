package com.example.inventory.data.repository.name

interface NameRepository {
    suspend fun getName(): String?
    suspend fun setName(newName: String)
    suspend fun removeName()
}