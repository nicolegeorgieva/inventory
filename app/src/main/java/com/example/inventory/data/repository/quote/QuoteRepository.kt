package com.example.inventory.data.repository.quote

import com.example.inventory.data.datasource.date.DateDataSource
import com.example.inventory.data.datasource.quote.LocalQuoteDataSource
import com.example.inventory.data.datasource.quote.RemoteQuoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject

class QuoteRepository @Inject constructor(
    private val remoteQuoteDataSource: RemoteQuoteDataSource,
    private val localQuoteDataSource: LocalQuoteDataSource,
    private val dateDataSource: DateDataSource
) {
    companion object {
        private const val DEFAULT_QUOTE = "Organization is power"
        private const val MILLIS_24_HOURS = 86400000
    }

    suspend fun getLocalOrDefaultQuote(): String {
        return withContext(Dispatchers.IO) {
            localQuoteDataSource.getQuote() ?: DEFAULT_QUOTE
        }
    }

    /**
     * It gets a random quote from the server and saves it in DataStore.
     * @return random quote from the fetched quotes response or lastly saved quote from DataStore
     * or a hard-coded default quote
     */
    suspend fun getQuoteWithRemoteCall(): String {
        return withContext(Dispatchers.IO) {
            val savedDate = dateDataSource.getDate()

            if (savedDate == null || twentyFourHoursHavePassed(savedDate)) {
                fetchRandomRemoteQuote() ?: getLocalOrDefaultQuote()
            } else {
                getLocalOrDefaultQuote()
            }
        }
    }

    private suspend fun fetchRandomRemoteQuote(): String? {
        val quote = try {
            val randomQuote = remoteQuoteDataSource.fetchQuotes().random()
            setQuote(randomQuote)
            randomQuote
        } catch (e: Exception) {
            null
        }

        return quote
    }

    private suspend fun setQuote(quote: String?) {
        withContext(Dispatchers.IO) {
            if (quote != null) {
                localQuoteDataSource.setQuote(quote)
                dateDataSource.setDate(Date().time)
            }
        }
    }

    private fun twentyFourHoursHavePassed(savedDate: Long): Boolean {
        return Date().time >= savedDate + MILLIS_24_HOURS
    }
}