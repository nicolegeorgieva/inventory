package com.example.inventory.domain

import java.util.Date
import javax.inject.Inject

class DateProvider @Inject constructor() {
    fun provideCurrentDate(): Long {
        return Date().time
    }
}