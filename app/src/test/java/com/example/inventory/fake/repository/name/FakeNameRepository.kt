package com.example.inventory.fake.repository.name

import com.example.inventory.data.repository.name.NameRepository

class FakeNameRepository : NameRepository {
    private var name: String? = null

    override suspend fun getName(): String? {
        return name
    }

    override suspend fun setName(newName: String) {
        if (newName.isBlank()) return

        name = newName.trim()
    }

    override suspend fun removeName() {
        name = null
    }
}