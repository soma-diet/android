package dev.skaba.soma.app.ui.features.entry.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dev.skaba.soma.app.domain.food.FoodRepository
import dev.skaba.soma.app.domain.food.Serving
import dev.skaba.soma.app.domain.log_entry.LogEntry
import dev.skaba.soma.app.domain.log_entry.LogEntryRepository
import dev.skaba.soma.app.ui.data.validateNumberNotEmpty
import dev.skaba.soma.app.ui.data.validateNumberNotNegative
import dev.skaba.soma.app.ui.navigation.EntryFormScreenRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID

class EntryFormViewModel(
  savedStateHandle: SavedStateHandle,
  private val logEntryRepository: LogEntryRepository,
  private val foodRepository: FoodRepository,
) : ViewModel() {
  private val _state = MutableStateFlow(EntryFormState())
  val state: StateFlow<EntryFormState> = _state.asStateFlow()

  private val route = savedStateHandle.toRoute<EntryFormScreenRoute>() // nacist route z argumentu
  private var existingEntryTimestamp: Long? = null // pro ulozeni originalniho timestampu entry

  init {
    loadEntryData()
  }

  private fun loadEntryData() {
    viewModelScope.launch {
      _state.update { it.copy(isLoading = true) }

      val entryId = route.entryId
      if (entryId != null) {
        // EDITOVANI
        val entry = logEntryRepository.getById(entryId)
        if (entry != null) {
          existingEntryTimestamp = entry.timestamp // ulozit originalni timestamp
          _state.update {
            it.copy(
              food = entry.food,
              quantity = it.quantity.copy(value = entry.quantity),
              selectedServing = it.selectedServing.copy(value = entry.serving),
              isEditMode = true,
              isLoading = false,
            )
          }
        }
      } else {
        // NOVY LOG
        val food = foodRepository.getById(route.foodId!!)

        _state.update {
          it.copy(
            food = food,
            isLoading = false,
          )
        }
      }
    }
  }

  fun onEvent(event: EntryFormEvent) {
    when (event) {
      is EntryFormEvent.QuantityChanged -> updateQuantity(event.quantity)
      is EntryFormEvent.ServingChanged -> updateServing(event.serving)
      is EntryFormEvent.SaveEntry -> saveEntry(event.onSuccess)
    }
  }

  private fun updateQuantity(quantity: Float?) {
    _state.update {
      it.copy(quantity = it.quantity.copy(value = quantity, error = null))
    }
  }

  private fun updateServing(serving: Serving?) {
    _state.update {
      it.copy(selectedServing = it.selectedServing.copy(value = serving))
    }
  }

  private fun saveEntry(onSuccess: () -> Unit) {
    _state.update {
      it.copy(
        quantity = it.quantity.validateNumberNotEmpty("Quantity is required")
          .validateNumberNotNegative("Quantity cannot be negative"),
      )
    }

    if (_state.value.quantity.error != null) return

    val currentState = _state.value
    val food = currentState.food ?: return

    viewModelScope.launch {
      _state.update { it.copy(isSaving = true) }
      try {
        val entry = LogEntry(
          id = route.entryId ?: UUID.randomUUID().toString(),
          timestamp = existingEntryTimestamp ?: Date().time,
          food = food,
          serving = currentState.selectedServing.value,
          quantity = currentState.quantity.value!!,
        )
        logEntryRepository.insert(entry)
        onSuccess()
      } catch (e: Exception) {
        e.printStackTrace()
      } finally {
        _state.update { it.copy(isSaving = false) }
      }
    }
  }
}
