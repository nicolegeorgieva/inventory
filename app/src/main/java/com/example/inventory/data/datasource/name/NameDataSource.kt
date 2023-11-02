package com.example.inventory.data.datasource.name

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.inventory.data.datastore.NAME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NameDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    fun getName(): Flow<String?> {
        return dataStore.data.map {
            it[NAME]
        }
    }

    suspend fun setName(newName: String) {
        dataStore.edit {
            it[NAME] = newName
        }
    }

    suspend fun removeName() {
        dataStore.edit {
            it.remove(NAME)
        }
    }
}