package com.example.inventory.screen.home

import com.example.inventory.data.model.InventoryItem
import com.example.inventory.data.repository.inventory.InventoryRepository
import com.example.inventory.data.repository.name.NameRepository
import com.example.inventory.data.repository.quote.QuoteRepository
import com.example.inventory.fake.repository.inventory.FakeInventoryRepository
import com.example.inventory.fake.repository.name.FakeNameRepository
import com.example.inventory.navigation.Navigator
import com.example.inventory.runTest
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.collections.immutable.persistentListOf
import java.util.UUID

class HomeViewModelTest : FreeSpec({
    "screen loaded" - {
        "empty list of items and no name" {
            // given
            val nameRepository: NameRepository = FakeNameRepository()
            val quoteRepository = mockk<QuoteRepository>()
            val inventoryRepository: InventoryRepository = FakeInventoryRepository()
            val navigator = mockk<Navigator>()
            val viewModel = HomeViewModel(
                nameRepository,
                quoteRepository,
                inventoryRepository,
                InventoryListProvider(),
                navigator
            )
            coEvery { quoteRepository.getQuoteWithRemoteCall() } returns "Organization is power"

            // when
            val events = emptyList<HomeEvent>()

            // then
            viewModel.runTest(events) {
                it.name shouldBe null
                it.quote shouldBe "Organization is power"
                it.inventoryItemList shouldBe persistentListOf()
            }
        }

        "list of items and name" {
            // given
            val nameRepository: NameRepository = FakeNameRepository()
            val quoteRepository = mockk<QuoteRepository>()
            val inventoryRepository: InventoryRepository = FakeInventoryRepository()
            val navigator = mockk<Navigator>()
            val viewModel = HomeViewModel(
                nameRepository,
                quoteRepository,
                inventoryRepository,
                InventoryListProvider(),
                navigator
            )
            val id = UUID.randomUUID()
            val inventoryItem = InventoryItem(
                id = id,
                name = "Kitchen roll",
                quantity = 4,
                minQuantityTarget = 5,
                category = "Groceries",
                description = "",
                imagePath = ""
            )
            coEvery { quoteRepository.getQuoteWithRemoteCall() } returns "Organization is power"

            // when
            nameRepository.setName("Amy")
            inventoryRepository.add(inventoryItem)
            val events = emptyList<HomeEvent>()

            // then
            val inventoryUi = InventoryItemUi(
                id = id.toString(),
                name = "Kitchen roll",
                quantity = "4",
                imagePath = "",
                category = "Groceries"
            )
            viewModel.runTest(events) {
                it.name shouldBe "Amy"
                it.quote shouldBe "Organization is power"
                it.inventoryItemList shouldBe persistentListOf(
                    InventoryItemType.Section(section = SectionType.TOBUY, count = 1),
                    InventoryItemType.Item(inventoryUi),
                    InventoryItemType.Section(section = SectionType.ENOUGH, count = 0)
                )
            }
        }
    }

    "increase quantity" {
        // given
        val nameRepository: NameRepository = FakeNameRepository()
        val quoteRepository = mockk<QuoteRepository>()
        val inventoryRepository: InventoryRepository = FakeInventoryRepository()
        val navigator = mockk<Navigator>()
        val viewModel = HomeViewModel(
            nameRepository, quoteRepository, inventoryRepository, InventoryListProvider(), navigator
        )
        val id = UUID.randomUUID()
        val inventoryItem = InventoryItem(
            id = id,
            name = "Kitchen roll",
            quantity = 4,
            minQuantityTarget = 5,
            category = "Groceries",
            description = "",
            imagePath = ""
        )
        coEvery { quoteRepository.getQuoteWithRemoteCall() } returns "Organization is power"

        // when
        nameRepository.setName("Amy")
        inventoryRepository.add(inventoryItem)
        val events = listOf(
            HomeEvent.IncreaseQuantity(id.toString()),
            HomeEvent.IncreaseQuantity(id.toString())
        )

        // then
        val inventoryUi = InventoryItemUi(
            id = id.toString(),
            name = "Kitchen roll",
            quantity = "6",
            imagePath = "",
            category = "Groceries"
        )
        viewModel.runTest(events) {
            it.name shouldBe "Amy"
            it.quote shouldBe "Organization is power"
            it.inventoryItemList shouldBe persistentListOf(
                InventoryItemType.Section(section = SectionType.TOBUY, count = 0),
                InventoryItemType.Section(section = SectionType.ENOUGH, count = 1),
                InventoryItemType.Item(inventoryUi)
            )
        }
    }

    "decrease quantity" {
        // given
        val nameRepository: NameRepository = FakeNameRepository()
        val quoteRepository = mockk<QuoteRepository>()
        val inventoryRepository: InventoryRepository = FakeInventoryRepository()
        val navigator = mockk<Navigator>()
        val viewModel = HomeViewModel(
            nameRepository, quoteRepository, inventoryRepository, InventoryListProvider(), navigator
        )
        val id = UUID.randomUUID()
        val inventoryItem = InventoryItem(
            id = id,
            name = "Kitchen roll",
            quantity = 4,
            minQuantityTarget = 5,
            category = "Groceries",
            description = "",
            imagePath = ""
        )
        coEvery { quoteRepository.getQuoteWithRemoteCall() } returns "Organization is power"

        // when
        inventoryRepository.add(inventoryItem)
        val events = listOf(HomeEvent.DecreaseQuantity(id.toString()))

        // then
        val inventoryUi = InventoryItemUi(
            id = id.toString(),
            name = "Kitchen roll",
            quantity = "3",
            imagePath = "",
            category = "Groceries"
        )
        viewModel.runTest(events) {
            it.name shouldBe null
            it.quote shouldBe "Organization is power"
            it.inventoryItemList shouldBe persistentListOf(
                InventoryItemType.Section(section = SectionType.TOBUY, count = 1),
                InventoryItemType.Item(inventoryUi),
                InventoryItemType.Section(section = SectionType.ENOUGH, count = 0)
            )
        }
    }
})