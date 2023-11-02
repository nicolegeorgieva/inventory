package com.example.inventory.data.repository.quote

import com.example.inventory.data.datasource.date.DateDataSource
import com.example.inventory.data.datasource.quote.LocalQuoteDataSource
import com.example.inventory.data.datasource.quote.RemoteQuoteDataSource
import com.example.inventory.dispatcher.DispatcherProvider
import com.example.inventory.domain.DateProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class QuoteRepository @Inject constructor(
    private val dispatchers: DispatcherProvider,
    private val remoteQuoteDataSource: RemoteQuoteDataSource,
    private val localQuoteDataSource: LocalQuoteDataSource,
    private val dateDataSource: DateDataSource,
    private val dateProvider: DateProvider
) {
    companion object {
        const val DEFAULT_QUOTE = "Organization is power"
        const val MILLIS_24_HOURS = 86400000L
    }

    fun getLocalOrDefaultQuote(): Flow<String> {
        return localQuoteDataSource.getQuote().map {
            it ?: DEFAULT_QUOTE
        }
    }

    /**
     * It gets a random quote from the server and saves it in DataStore.
     * @return random quote from the fetched quotes response or lastly saved quote from DataStore
     * or a hard-coded default quote
     */
    suspend fun getQuoteWithRemoteCall(): Flow<String> {
        val savedDateFlow = dateDataSource.getDate()
        val savedDate = savedDateFlow.first()

        return flow {
            if (savedDate == null || twentyFourHoursHavePassed(savedDate)) {
                fetchRandomRemoteQuote().map {
                    it ?: getLocalOrDefaultQuote()
                }
            } else {
                getLocalOrDefaultQuote()
            }.flowOn(dispatchers.io)
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
        withContext(dispatchers.io) {
            if (quote != null) {
                localQuoteDataSource.setQuote(quote)
                dateDataSource.setDate(dateProvider.provideCurrentDate())
            }
        }
    }

    private fun twentyFourHoursHavePassed(savedDate: Long): Boolean {
        return dateProvider.provideCurrentDate() >= savedDate + MILLIS_24_HOURS
    }
}