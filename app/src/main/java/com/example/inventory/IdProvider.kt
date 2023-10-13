package com.example.inventory

import java.util.UUID
import javax.inject.Inject

class IdProvider @Inject constructor() {
    fun generateId(): UUID {
        return UUID.randomUUID()
    }
}