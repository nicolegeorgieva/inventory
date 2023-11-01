package com.example.inventory.data.repository.quote

import com.example.inventory.data.datasource.quote.LocalQuoteDataSource
import com.example.inventory.data.datasource.quote.RemoteQuoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class QuoteRepository @Inject constructor(
    private val remoteQuoteDataSource: RemoteQuoteDataSource,
    private val localQuoteDataSource: LocalQuoteDataSource
) {
    suspend fun getQuote(): String {
        var quote: String
        val defaultQuote = "Organization is power"

        withContext(Dispatchers.IO) {
            quote = try {
                val randomQuote = remoteQuoteDataSource.fetchQuotes().random()
                setQuote(randomQuote)
                randomQuote
            } catch (e: Exception) {
                localQuoteDataSource.getQuote() ?: defaultQuote
            }
        }

        return quote
    }

    suspend fun setQuote(quote: String?) {
        withContext(Dispatchers.IO) {
            if (quote != null) {
                localQuoteDataSource.setQuote(quote)
            }
        }
    }
}