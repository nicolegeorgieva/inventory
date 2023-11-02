package com.example.inventory.data.repository

import com.example.inventory.data.datasource.date.DateDataSource
import com.example.inventory.data.datasource.quote.LocalQuoteDataSource
import com.example.inventory.data.datasource.quote.RemoteQuoteDataSource
import com.example.inventory.data.repository.quote.QuoteRepository
import com.example.inventory.domain.DateProvider
import com.example.inventory.fake.dispatcher.FakeDispatcherProvider
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk

class QuoteRepositoryTest : FreeSpec({
    "get local or default quote" - {
        "getQuote is null" {
            // given
            val remoteQuoteDataSource = mockk<RemoteQuoteDataSource>()
            val localQuoteDataSource = mockk<LocalQuoteDataSource>()
            val dateDataSource = mockk<DateDataSource>()
            val dateProvider = mockk<DateProvider>()

            val repository = QuoteRepository(
                dispatchers = FakeDispatcherProvider(),
                remoteQuoteDataSource = remoteQuoteDataSource,
                localQuoteDataSource = localQuoteDataSource,
                dateDataSource = dateDataSource,
                dateProvider = dateProvider
            )
            coEvery { localQuoteDataSource.getQuote() } returns null

            // when
            val quote = repository.getLocalOrDefaultQuote()

            // then
            quote shouldBe QuoteRepository.DEFAULT_QUOTE
        }

        "available getQuote" {
            // given
            val remoteQuoteDataSource = mockk<RemoteQuoteDataSource>()
            val localQuoteDataSource = mockk<LocalQuoteDataSource>()
            val dateDataSource = mockk<DateDataSource>()
            val dateProvider = mockk<DateProvider>()
            val savedQuote = "Preparation is power"

            val repository = QuoteRepository(
                dispatchers = FakeDispatcherProvider(),
                remoteQuoteDataSource = remoteQuoteDataSource,
                localQuoteDataSource = localQuoteDataSource,
                dateDataSource = dateDataSource,
                dateProvider = dateProvider
            )
            coEvery { localQuoteDataSource.getQuote() } returns savedQuote

            // when
            val quote = repository.getLocalOrDefaultQuote()

            // then
            quote shouldBe savedQuote
        }
    }
})