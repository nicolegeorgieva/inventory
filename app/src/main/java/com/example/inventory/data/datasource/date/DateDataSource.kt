package com.example.inventory.data.datasource.date

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.inventory.data.datastore.DATE
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DateDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    suspend fun getDate(): Long? {
        return dataStore.data.map {
            it[DATE]
        }.firstOrNull()
    }

    suspend fun setDate(date: Long) {
        dataStore.edit {
            it[DATE] = date
        }
    }
}