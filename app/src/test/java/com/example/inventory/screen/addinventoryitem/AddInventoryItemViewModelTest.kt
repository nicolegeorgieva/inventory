package com.example.inventory.screen.addinventoryitem

import com.example.inventory.IdProvider
import com.example.inventory.data.model.InventoryItem
import com.example.inventory.data.repository.InventoryRepository
import com.example.inventory.runTest
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import java.util.UUID

class AddInventoryItemViewModelTest : FreeSpec({
    "initial state" {
        // given
        val repository = mockk<InventoryRepository>()
        val idProvider = mockk<IdProvider>()
        val viewModel = AddInventoryItemViewModel(repository, idProvider)

        // when
        val events = emptyList<AddInventoryItemEvent>()

        // then
        viewModel.runTest(events = events) {
            it.name shouldBe null
            it.quantity shouldBe null
            it.minQuantityTarget shouldBe null
            it.category shouldBe null
            it.description shouldBe null
            it.imagePath shouldBe null
        }
    }

    "set name" {
        // given
        val repository = mockk<InventoryRepository>()
        val idProvider = mockk<IdProvider>()
        val viewModel = AddInventoryItemViewModel(repository, idProvider)

        // when
        val events = listOf(AddInventoryItemEvent.SetName("Kitchen roll"))

        // then
        viewModel.runTest(events = events) {
            it.name shouldBe "Kitchen roll"
        }
    }

    "add inventory item" - {
        "valid case" {
            // given
            val repository = mockk<InventoryRepository>()
            val idProvider = mockk<IdProvider>()
            val viewModel = AddInventoryItemViewModel(repository, idProvider)
            val id = UUID.randomUUID()
            val inventoryItem = InventoryItem(
                id = id,
                name = "Kitchen roll",
                quantity = 4,
                minQuantityTarget = 5,
                category = "",
                description = "",
                imagePath = ""
            )
            coEvery { repository.add(any()) } just runs
            coEvery { idProvider.generateId() } returns id

            // when
            val events = listOf(
                AddInventoryItemEvent.SetName("Kitchen roll"),
                AddInventoryItemEvent.SetQuantity("4"),
                AddInventoryItemEvent.SetMinQuantityTarget("5"),
                AddInventoryItemEvent.AddInventoryItem
            )

            // then
            viewModel.runTest(events = events) {
                coVerify(exactly = 1) {
                    repository.add(inventoryItem)
                }
            }
        }
    }
})