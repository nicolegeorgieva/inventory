package com.example.inventory.screen.home

import com.example.inventory.data.model.InventoryItem
import com.example.inventory.data.repository.inventory.InventoryRepository
import com.example.inventory.data.repository.name.NameRepository
import com.example.inventory.fake.repository.inventory.FakeInventoryRepository
import com.example.inventory.fake.repository.name.FakeNameRepository
import com.example.inventory.runTest
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import kotlinx.collections.immutable.persistentListOf
import java.util.UUID

class HomeViewModelTest : FreeSpec({
    "screen loaded" - {
        "empty list of items and no name" {
            // given
            val nameRepository: NameRepository = FakeNameRepository()
            val inventoryRepository: InventoryRepository = FakeInventoryRepository()
            val viewModel = HomeViewModel(nameRepository, inventoryRepository)

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
            val nameRepository: NameRepository = FakeNameRepository()
            val inventoryRepository: InventoryRepository = FakeInventoryRepository()
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

            // when
            nameRepository.setName("Amy")
            inventoryRepository.add(inventoryItem)
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
        val nameRepository: NameRepository = FakeNameRepository()
        val inventoryRepository: InventoryRepository = FakeInventoryRepository()
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
            quantity = "5",
            imagePath = "",
            category = "Groceries"
        )

        // when
        nameRepository.setName("Amy")
        inventoryRepository.add(inventoryItem)
        val events = listOf(HomeEvent.IncreaseQuantity(id.toString()))

        // then
        viewModel.runTest(events) {
            it.name shouldBe "Amy"
            it.inventoryList shouldBe persistentListOf(inventoryUi)
        }
    }

    "decrease quantity" {
        // given
        val nameRepository: NameRepository = FakeNameRepository()
        val inventoryRepository: InventoryRepository = FakeInventoryRepository()
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
            quantity = "3",
            imagePath = "",
            category = "Groceries"
        )

        // when
        inventoryRepository.add(inventoryItem)
        val events = listOf(HomeEvent.DecreaseQuantity(id.toString()))

        // then
        viewModel.runTest(events) {
            it.name shouldBe null
            it.inventoryList shouldBe persistentListOf(inventoryUi)
        }
    }
})