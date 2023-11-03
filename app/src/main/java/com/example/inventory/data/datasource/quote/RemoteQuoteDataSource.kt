package com.example.inventory.data.datasource.quote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.serialization.Serializable
import javax.inject.Inject

class RemoteQuoteDataSource @Inject constructor(
    private val client: HttpClient
) {
    @Serializable
    data class Quotes(
        val quotes: List<String>
    )

    suspend fun fetchQuotes(): List<String> {
        return client.get(
            "https://raw.githubusercontent.com/nicolegeorgieva/inventory/main/" +
                    "app/src/main/java/com/example/inventory/quotes.json"
        ).body<Quotes>().quotes
    }
}