package com.example.inventory.screen.addoreditinventoryitem

import com.example.inventory.data.repository.inventory.InventoryRepository
import com.example.inventory.domain.IdProvider
import com.example.inventory.fake.repository.inventory.FakeInventoryRepository
import com.example.inventory.navigation.Navigator
import com.example.inventory.runTest
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import java.util.UUID

class AddInventoryItemViewModelTest : FreeSpec({
    "screen loaded" {
        // given
        val repository: InventoryRepository = FakeInventoryRepository()
        val idProvider = mockk<IdProvider>()
        val navigator = mockk<Navigator>()
        val viewModel = AddEditInventoryItemViewModel(repository, idProvider, navigator)

        // when
        val events = emptyList<AddEditInventoryItemEvent>()

        // then
        viewModel.runTest(events = events) {
            it.name shouldBe null
            it.quantity shouldBe null
            it.minQuantityTarget shouldBe null
            it.category shouldBe null
            it.description shouldBe null
            it.imagePath shouldBe null
            it.link shouldBe null
            it.addWithoutRequired shouldBe false
        }
    }

    "setName" {
        // given
        val repository: InventoryRepository = FakeInventoryRepository()
        val idProvider = mockk<IdProvider>()
        val navigator = mockk<Navigator>()
        val viewModel = AddEditInventoryItemViewModel(repository, idProvider, navigator)

        // when
        val events = listOf<AddEditInventoryItemEvent>(
            AddEditInventoryItemEvent.SetName("Kitchen roll")
        )

        // then
        viewModel.runTest(events = events) {
            it.name shouldBe "Kitchen roll"
            it.quantity shouldBe null
            it.minQuantityTarget shouldBe null
            it.category shouldBe null
            it.description shouldBe null
            it.imagePath shouldBe null
            it.link shouldBe null
            it.addWithoutRequired shouldBe true
        }
    }

    "setName and setQuantity" {
        // given
        val repository: InventoryRepository = FakeInventoryRepository()
        val idProvider = mockk<IdProvider>()
        val navigator = mockk<Navigator>()
        val viewModel = AddEditInventoryItemViewModel(repository, idProvider, navigator)

        // when
        val events = listOf(
            AddEditInventoryItemEvent.SetName("Kitchen roll"),
            AddEditInventoryItemEvent.SetQuantity("5")
        )

        // then
        viewModel.runTest(events) {
            it.name shouldBe "Kitchen roll"
            it.quantity shouldBe "5"
            it.minQuantityTarget shouldBe null
            it.category shouldBe null
            it.description shouldBe null
            it.imagePath shouldBe null
            it.link shouldBe null
            it.addWithoutRequired shouldBe true
        }
    }

    "setMinQuantityTarget" {
        // given
        val repository: InventoryRepository = FakeInventoryRepository()
        val idProvider = mockk<IdProvider>()
        val navigator = mockk<Navigator>()
        val viewModel = AddEditInventoryItemViewModel(repository, idProvider, navigator)

        // when
        val events = listOf(
            AddEditInventoryItemEvent.SetMinQuantityTarget("10")
        )

        // then
        viewModel.runTest(events) {
            it.name shouldBe null
            it.quantity shouldBe null
            it.minQuantityTarget shouldBe "10"
            it.category shouldBe null
            it.description shouldBe null
            it.imagePath shouldBe null
            it.link shouldBe null
            it.addWithoutRequired shouldBe true
        }
    }

    "setCategory" {
        // given
        val repository: InventoryRepository = FakeInventoryRepository()
        val idProvider = mockk<IdProvider>()
        val navigator = mockk<Navigator>()
        val viewModel = AddEditInventoryItemViewModel(repository, idProvider, navigator)

        // when
        val events = listOf(
            AddEditInventoryItemEvent.SetCategory("Groceries")
        )

        // then
        viewModel.runTest(events) {
            it.name shouldBe null
            it.quantity shouldBe null
            it.minQuantityTarget shouldBe null
            it.category shouldBe "Groceries"
            it.description shouldBe null
            it.imagePath shouldBe null
            it.link shouldBe null
            it.addWithoutRequired shouldBe false
        }
    }

    "setDescription" {
        // given
        val repository: InventoryRepository = FakeInventoryRepository()
        val idProvider = mockk<IdProvider>()
        val navigator = mockk<Navigator>()
        val viewModel = AddEditInventoryItemViewModel(repository, idProvider, navigator)

        // when
        val events = listOf(
            AddEditInventoryItemEvent.SetDescription("...")
        )

        // then
        viewModel.runTest(events) {
            it.name shouldBe null
            it.quantity shouldBe null
            it.minQuantityTarget shouldBe null
            it.category shouldBe null
            it.description shouldBe "..."
            it.imagePath shouldBe null
            it.link shouldBe null
            it.addWithoutRequired shouldBe false
        }
    }

    "setLinkImage" {
        // given
        val repository: InventoryRepository = FakeInventoryRepository()
        val idProvider = mockk<IdProvider>()
        val navigator = mockk<Navigator>()
        val viewModel = AddEditInventoryItemViewModel(repository, idProvider, navigator)

        // when
        val events = listOf(
            AddEditInventoryItemEvent.OnLinkValueChange(
                "https://media.kitepackaging.co.uk/images/product/large/7x7x7l.jpg"
            ),
            AddEditInventoryItemEvent.SetLinkImage(
                "https://media.kitepackaging.co.uk/images/product/large/7x7x7l.jpg"
            )
        )

        // then
        viewModel.runTest(events) {
            it.name shouldBe null
            it.quantity shouldBe null
            it.minQuantityTarget shouldBe null
            it.category shouldBe null
            it.description shouldBe null
            it.imagePath shouldBe "https://media.kitepackaging.co.uk/images/" +
                    "product/large/7x7x7l.jpg"
            it.link shouldBe null
            it.addWithoutRequired shouldBe false
        }
    }

    "onLinkValueChange" {
        // given
        val repository: InventoryRepository = FakeInventoryRepository()
        val idProvider = mockk<IdProvider>()
        val navigator = mockk<Navigator>()
        val viewModel = AddEditInventoryItemViewModel(repository, idProvider, navigator)

        // when
        val events = listOf(
            AddEditInventoryItemEvent.OnLinkValueChange(
                "https://media.kitepackaging.co.uk/images/product/large/7x7x7l.jpg"
            )
        )

        // then
        viewModel.runTest(events) {
            it.name shouldBe null
            it.quantity shouldBe null
            it.minQuantityTarget shouldBe null
            it.category shouldBe null
            it.description shouldBe null
            it.imagePath shouldBe null
            it.link shouldBe "https://media.kitepackaging.co.uk/images/product/large/7x7x7l.jpg"
            it.addWithoutRequired shouldBe false
        }
    }

    "add inventory item" - {
        "valid case" {
            // given
            val repository: InventoryRepository = FakeInventoryRepository()
            val idProvider = mockk<IdProvider>()
            val navigator = mockk<Navigator>()
            val viewModel = AddEditInventoryItemViewModel(repository, idProvider, navigator)
            val id = UUID.randomUUID()
            coEvery { idProvider.generateId() } returns id
            coEvery { navigator.back() } just runs

            // when
            val events = listOf(
                AddEditInventoryItemEvent.SetName("Kitchen roll"),
                AddEditInventoryItemEvent.SetQuantity("4"),
                AddEditInventoryItemEvent.SetMinQuantityTarget("5"),
                AddEditInventoryItemEvent.AddInventoryItem
            )

            // then
            viewModel.runTest(events = events) {
                it.name shouldBe "Kitchen roll"
                it.quantity shouldBe "4"
                it.minQuantityTarget shouldBe "5"
                it.category shouldBe null
                it.description shouldBe null
                it.imagePath shouldBe null
                it.link shouldBe null
                it.addWithoutRequired shouldBe false
            }
        }
        "invalid case" {
            // given
            val repository: InventoryRepository = FakeInventoryRepository()
            val idProvider = mockk<IdProvider>()
            val navigator = mockk<Navigator>()
            val viewModel = AddEditInventoryItemViewModel(repository, idProvider, navigator)

            // when
            val events = listOf(
                AddEditInventoryItemEvent.SetName("Item"),
                AddEditInventoryItemEvent.SetQuantity("2.5"),
                AddEditInventoryItemEvent.SetMinQuantityTarget("3"),
                AddEditInventoryItemEvent.AddInventoryItem
            )

            // then
            viewModel.runTest(events) {
                it.name shouldBe "Item"
                it.quantity shouldBe "2.5"
                it.minQuantityTarget shouldBe "3"
                it.category shouldBe null
                it.description shouldBe null
                it.imagePath shouldBe null
                it.link shouldBe null
                it.addWithoutRequired shouldBe true
            }
        }
    }
})