package com.example.inventory.data.datasource.quote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.Serializable
import javax.inject.Inject

class RemoteQuoteDataSource @Inject constructor(
    private val client: HttpClient
) {
    @Serializable
    data class Quotes(
        val quotes: List<String>
    )

    fun fetchQuotes(): Flow<List<String>> {
        return flow {
            val quotes = client.get(
                "https://raw.githubusercontent.com/nicolegeorgieva/inventory/main/" +
                        "app/src/main/java/com/example/inventory/quotes.json"
            ).body<Quotes>().quotes

            emit(quotes)
        }
    }
}