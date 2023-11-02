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
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs

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

    "getQuoteWithRemoteCall" - {
        "savedDate is null and fetchRandomRemoteQuote is emptyList" {
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

            coEvery { dateDataSource.getDate() } returns null
            coEvery { dateDataSource.setDate(any()) } just runs
            coEvery { localQuoteDataSource.getQuote() } returns savedQuote
            coEvery { remoteQuoteDataSource.fetchQuotes() } returns listOf()

            // when
            val quote = repository.getQuoteWithRemoteCall()

            // then
            quote shouldBe savedQuote
        }

        "twentyFourHoursHavePassed" {
            // given
            val remoteQuoteDataSource = mockk<RemoteQuoteDataSource>()
            val localQuoteDataSource = mockk<LocalQuoteDataSource>()
            val dateDataSource = mockk<DateDataSource>()
            val dateProvider = mockk<DateProvider>()
            val savedQuote = "Preparation is power"
            val remoteQuote = "Prepare today. Appreciate tomorrow"
            val timeBefore = QuoteRepository.MILLIS_24_HOURS
            val timeNow = timeBefore * 3

            val repository = QuoteRepository(
                dispatchers = FakeDispatcherProvider(),
                remoteQuoteDataSource = remoteQuoteDataSource,
                localQuoteDataSource = localQuoteDataSource,
                dateDataSource = dateDataSource,
                dateProvider = dateProvider
            )

            coEvery { dateDataSource.getDate() } returns timeBefore
            coEvery { dateDataSource.setDate(any()) } just runs
            coEvery { dateProvider.provideCurrentDate() } returns timeNow
            coEvery { localQuoteDataSource.getQuote() } returns savedQuote
            coEvery { localQuoteDataSource.setQuote(any()) } just runs
            coEvery {
                remoteQuoteDataSource.fetchQuotes()
            } returns listOf(remoteQuote)

            // when
            val quote = repository.getQuoteWithRemoteCall()

            // then
            quote shouldBe remoteQuote
            coVerify(exactly = 1) {
                localQuoteDataSource.setQuote(remoteQuote)
            }
            coVerify(exactly = 1) {
                dateDataSource.setDate(timeNow)
            }
        }

        "returns local or default quote" {
            // given
            val remoteQuoteDataSource = mockk<RemoteQuoteDataSource>()
            val localQuoteDataSource = mockk<LocalQuoteDataSource>()
            val dateDataSource = mockk<DateDataSource>()
            val dateProvider = mockk<DateProvider>()
            val savedQuote = "Preparation is power"
            val timeBefore = QuoteRepository.MILLIS_24_HOURS
            val timeNow = timeBefore + 1000

            val repository = QuoteRepository(
                dispatchers = FakeDispatcherProvider(),
                remoteQuoteDataSource = remoteQuoteDataSource,
                localQuoteDataSource = localQuoteDataSource,
                dateDataSource = dateDataSource,
                dateProvider = dateProvider
            )

            coEvery { dateDataSource.getDate() } returns timeBefore
            coEvery { localQuoteDataSource.getQuote() } returns savedQuote
            coEvery { dateDataSource.setDate(any()) } just runs
            coEvery { dateProvider.provideCurrentDate() } returns timeNow

            // when
            val quote = repository.getQuoteWithRemoteCall()

            // then
            quote shouldBe savedQuote
            coVerify(exactly = 0) {
                remoteQuoteDataSource.fetchQuotes()
            }
        }
    }
})