package com.example.inventory.screen.home

import com.example.inventory.data.model.InventoryItem
import com.example.inventory.data.repository.InventoryRepository
import com.example.inventory.data.repository.NameRepository
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
            val nameRepository = mockk<NameRepository>()
            val inventoryRepository = mockk<InventoryRepository>()
            val viewModel = HomeViewModel(nameRepository, inventoryRepository)

            coEvery { nameRepository.getName() } returns null
            coEvery { inventoryRepository.getAll() } returns emptyList()

            // when
            val events = emptyList<HomeEvent>()

            // then
            viewModel.runTest(events) {
                it.name shouldBe null
                it.inventoryList shouldBe emptyList()
            }
        }

        "list of items and name" {
            // given
            val nameRepository = mockk<NameRepository>()
            val inventoryRepository = mockk<InventoryRepository>()
            val viewModel = HomeViewModel(nameRepository, inventoryRepository)
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
            val inventoryUi = InventoryUi(
                id = id.toString(),
                name = "Kitchen roll",
                quantity = "4",
                imagePath = "",
                category = "Groceries"
            )

            coEvery { nameRepository.getName() } returns "Amy"
            coEvery { inventoryRepository.getAll() } returns listOf(inventoryItem)

            // when
            val events = emptyList<HomeEvent>()

            // then
            viewModel.runTest(events) {
                it.name shouldBe "Amy"
                it.inventoryList shouldBe persistentListOf(inventoryUi)
            }
        }
    }
})