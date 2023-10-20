package com.example.inventory.domain

import java.util.UUID
import javax.inject.Inject

class IdProvider @Inject constructor() {
    fun generateId(): UUID {
        return UUID.randomUUID()
    }
}