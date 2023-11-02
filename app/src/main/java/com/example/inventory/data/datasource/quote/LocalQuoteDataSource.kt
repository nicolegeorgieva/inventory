package com.example.inventory.data.datasource.quote

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.inventory.data.datastore.QUOTE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalQuoteDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    fun getQuote(): Flow<String?> {
        return dataStore.data.map {
            it[QUOTE]
        }
    }

    suspend fun setQuote(quote: String) {
        dataStore.edit {
            it[QUOTE] = quote
        }
    }
}