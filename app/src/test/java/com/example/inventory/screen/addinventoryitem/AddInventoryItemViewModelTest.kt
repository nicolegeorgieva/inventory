package com.example.inventory.screen.addinventoryitem

import com.example.inventory.data.repository.InventoryRepository
import com.example.inventory.runTest
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.mockk.mockk

class AddInventoryItemViewModelTest : FreeSpec({
    "initial state" {
        // given
        val repository = mockk<InventoryRepository>()
        val viewModel = AddInventoryItemViewModel(repository)

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
        val viewModel = AddInventoryItemViewModel(repository)

        // when
        val events = listOf(AddInventoryItemEvent.SetName("Kitchen roll"))

        // then
        viewModel.runTest(events = events) {
            it.name shouldBe "Kitchen roll"
        }
    }
})