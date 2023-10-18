package com.example.inventory.screen.home

import com.example.inventory.data.model.InventoryItem
import com.example.inventory.data.repository.inventory.InventoryRepository
import com.example.inventory.data.repository.inventory.InventoryRepositoryImpl
import com.example.inventory.data.repository.name.NameRepositoryImpl
import com.example.inventory.fake.repository.inventory.FakeInventoryRepository
import com.example.inventory.runTest
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.collections.immutable.persistentListOf
import java.util.UUID

class HomeViewModelTest : FreeSpec({
    "screen loaded" - {
        "empty list of items and no name" {
            // given
            val nameRepository = mockk<NameRepositoryImpl>()
            val inventoryRepository: InventoryRepository = FakeInventoryRepository()
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
            val nameRepository = mockk<NameRepositoryImpl>()
            val inventoryRepository = mockk<InventoryRepositoryImpl>()
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

    "add quantity" {
        // given
        val nameRepository = mockk<NameRepositoryImpl>()
        val inventoryRepository = mockk<InventoryRepositoryImpl>()
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

        coEvery { nameRepository.getName() } returns "Amy"
        coEvery { inventoryRepository.getAll() } returns listOf(inventoryItem)
        coEvery { inventoryRepository.getById(id) } returns inventoryItem
        coEvery { inventoryRepository.update(any()) } just runs

        // when
        val events = listOf(HomeEvent.IncreaseQuantity(id.toString()))

        // then
        viewModel.runTest(events) {
            coVerify(exactly = 1) {
                inventoryRepository.update(inventoryItem.copy(quantity = 5))
            }

            coVerify(exactly = 2) {
                inventoryRepository.getAll()
            }
        }
    }

    "decrease quantity" {
        // given
        val nameRepository = mockk<NameRepositoryImpl>()
        val inventoryRepository = mockk<InventoryRepositoryImpl>()
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

        coEvery { nameRepository.getName() } returns "Amy"
        coEvery { inventoryRepository.getAll() } returns listOf(inventoryItem)
        coEvery { inventoryRepository.getById(id) } returns inventoryItem
        coEvery { inventoryRepository.update(any()) } just runs

        // when
        val events = listOf(HomeEvent.DecreaseQuantity(id.toString()))

        // then
        viewModel.runTest(events) {
            coVerify(exactly = 1) {
                inventoryRepository.update(inventoryItem.copy(quantity = 3))
            }

            coVerify(exactly = 2) {
                inventoryRepository.getAll()
            }
        }
    }
})