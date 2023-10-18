package com.example.inventory.fake.repository.name

import com.example.inventory.data.repository.name.NameRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FakeNameRepository : NameRepository {
    private var name: String? = null

    override suspend fun getName(): String? {
        return withContext(Dispatchers.IO) {
            name
        }
    }

    override suspend fun setName(newName: String) {
        if (newName.isBlank()) return

        withContext(Dispatchers.IO) {
            name = newName.trim()
        }
    }

    override suspend fun removeName() {
        withContext(Dispatchers.IO) {
            name = null
        }
    }
}