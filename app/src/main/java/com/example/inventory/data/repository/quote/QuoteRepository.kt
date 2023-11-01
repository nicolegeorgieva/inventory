package com.example.inventory.data.repository.quote

import com.example.inventory.data.datasource.date.DateDataSource
import com.example.inventory.data.datasource.quote.LocalQuoteDataSource
import com.example.inventory.data.datasource.quote.RemoteQuoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class QuoteRepository @Inject constructor(
    private val remoteQuoteDataSource: RemoteQuoteDataSource,
    private val localQuoteDataSource: LocalQuoteDataSource,
    private val dateDataSource: DateDataSource
) {
    companion object {
        private const val DEFAULT_QUOTE = "Organization is power"
    }

    suspend fun getQuote(): String {
        return localQuoteDataSource.getQuote() ?: DEFAULT_QUOTE
    }

    /**
     * It gets a random quote from the server and saves it in DataStore.
     * @return random quote from the fetched quotes response or lastly saved quote from DataStore
     * or a hard-coded default quote
     */
    suspend fun fetchQuote(): String {
        var quote: String

        withContext(Dispatchers.IO) {
            quote = try {
                val randomQuote = remoteQuoteDataSource.fetchQuotes().random()
                setQuote(randomQuote)
                randomQuote
            } catch (e: Exception) {
                localQuoteDataSource.getQuote() ?: DEFAULT_QUOTE
            }
        }

        return quote
    }

    private suspend fun setQuote(quote: String?) {
        withContext(Dispatchers.IO) {
            if (quote != null) {
                localQuoteDataSource.setQuote(quote)
            }
        }
    }
}