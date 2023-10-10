package com.example.inventory.data.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.inventory.data.datastore.NAME
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NameDataSource @Inject constructor(
    private val datastore: DataStore<Preferences>
) {
    suspend fun getName(): String? {
        return datastore.data.map {
            it[NAME]
        }.firstOrNull()
    }

    suspend fun setName(newName: String) {
        datastore.edit {
            it[NAME] = newName
        }
    }

    suspend fun removeName() {
        datastore.edit {
            it.remove(NAME)
        }
    }
}