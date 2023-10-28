package com.example.inventory.data.repository.quote

import android.util.Log
import com.example.inventory.data.datasource.QuoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class QuoteRepository @Inject constructor(
    private val quoteDataSource: QuoteDataSource
) {
    suspend fun getQuotes(): List<String> {
        var quotes: List<String>

        withContext(Dispatchers.IO) {
            quotes = try {
                quoteDataSource.fetchQuotes()
            } catch (e: Exception) {
                Log.e("error", "$e")
                listOf()
            }
        }

        return quotes
    }
}