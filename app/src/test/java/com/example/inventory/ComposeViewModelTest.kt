package com.example.inventory

import app.cash.molecule.RecompositionMode
import app.cash.molecule.moleculeFlow
import app.cash.turbine.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.setMain

/**
 * Runs a [ComposeViewModel] test simulation.
 * Compose runtime effects are executed [RecompositionMode.Immediate].
 * @param events pass the events that have occurred in your simulation
 * @param verify assert what's the expected state after all the events
 */
@OptIn(ExperimentalCoroutinesApi::class)
fun <UiState, UiEvent> ComposeViewModel<UiState, UiEvent>.runTest(
    events: List<UiEvent>,
    verify: (UiState) -> Unit
) {
    Dispatchers.setMain(Dispatchers.Unconfined)
    val viewModel = this
    kotlinx.coroutines.test.runTest {
        moleculeFlow(mode = RecompositionMode.Immediate) {
            viewModel.uiState()
        }.test {
            events.onEach(viewModel::onEvent)
            verify(expectMostRecentItem())
            cancel()
        }
    }
}