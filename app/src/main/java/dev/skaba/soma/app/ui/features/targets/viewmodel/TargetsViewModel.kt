package dev.skaba.soma.app.ui.features.targets.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.skaba.soma.app.domain.targets.Targets
import dev.skaba.soma.app.domain.targets.TargetsRepository
import dev.skaba.soma.app.ui.data.validateNumberNotNegative
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TargetsViewModel(
  private val targetsRepository: TargetsRepository,
) : ViewModel() {
  private val _state = MutableStateFlow(TargetsFormState())
  val state: StateFlow<TargetsFormState> = _state.asStateFlow()

  init {
    viewModelScope.launch {
      _state.update {
        it.copy(
          isLoading = true,
        )
      }
      targetsRepository.get().collect { targets ->
        loadTargetsIntoState(targets)
        _state.update {
          it.copy(
            isLoading = false,
          )
        }
      }
    }
  }

  fun onEvent(event: TargetsFormEvent) {
    when (event) {
      is TargetsFormEvent.KcalChanged -> updateKcal(event.value)
      is TargetsFormEvent.CarbsChanged -> updateCarbs(event.value)
      is TargetsFormEvent.ProteinChanged -> updateProtein(event.value)
      is TargetsFormEvent.FatsChanged -> updateFats(event.value)
      is TargetsFormEvent.FiberChanged -> updateFiber(event.value)
      is TargetsFormEvent.SodiumChanged -> updateSodium(event.value)
      is TargetsFormEvent.SaveTargets -> updateTargets(event.onSuccess)
    }
  }

  private fun loadTargetsIntoState(targets: Targets) {
    _state.update {
      it.copy(
        kcal = it.kcal.copy(value = targets.kcal),
        carbs = it.carbs.copy(value = targets.carbs),
        protein = it.protein.copy(value = targets.protein),
        fats = it.fats.copy(value = targets.fats),
        fiber = it.fiber.copy(value = targets.fiber),
        sodium = it.sodium.copy(value = targets.sodium),
      )
    }
  }

  /* #region updates */

  fun updateKcal(value: Float?) {
    _state.update {
      it.copy(
        kcal = it.kcal.copy(
          value = value,
          error = null,
        ),
      )
    }
  }

  fun updateCarbs(value: Float?) {
    _state.update {
      it.copy(
        carbs = it.carbs.copy(
          value = value,
          error = null,
        ),
      )
    }
  }

  fun updateProtein(value: Float?) {
    _state.update {
      it.copy(
        protein = it.protein.copy(
          value = value,
          error = null,
        ),
      )
    }
  }

  fun updateFats(value: Float?) {
    _state.update {
      it.copy(
        fats = it.fats.copy(
          value = value,
          error = null,
        ),
      )
    }
  }

  fun updateFiber(value: Float?) {
    _state.update {
      it.copy(
        fiber = it.fiber.copy(
          value = value,
          error = null,
        ),
      )
    }
  }

  fun updateSodium(value: Float?) {
    _state.update {
      it.copy(
        sodium = it.sodium.copy(
          value = value,
          error = null,
        ),
      )
    }
  }

  /* #endregion */

  fun updateTargets(onSuccess: () -> Unit) {
    _state.update { current ->
      current.copy(
        kcal = current.kcal.validateNumberNotNegative("Calories cannot be negative"),
        carbs = current.carbs.validateNumberNotNegative("Carbohydrates cannot be negative"),
        protein = current.protein.validateNumberNotNegative("Protein cannot be negative"),
        fats = current.fats.validateNumberNotNegative("Fats cannot be negative"),
        fiber = current.fiber.validateNumberNotNegative("Fiber cannot be negative"),
        sodium = current.sodium.validateNumberNotNegative("Sodium cannot be negative"),
      )
    }

    val validatedState = _state.value
    val hasError = validatedState.kcal.error != null ||
        validatedState.carbs.error != null ||
        validatedState.protein.error != null ||
        validatedState.fats.error != null ||
        validatedState.fiber.error != null ||
        validatedState.sodium.error != null

    if (hasError) return

    _state.update { it.copy(isSaving = true) }

    viewModelScope.launch {
      try {
        val targets = Targets(
          kcal = validatedState.kcal.value,
          carbs = validatedState.carbs.value,
          protein = validatedState.protein.value,
          fats = validatedState.fats.value,
          fiber = validatedState.fiber.value,
          sodium = validatedState.sodium.value,
        )
        targetsRepository.update(targets)
        onSuccess()
      } catch (e: Exception) {
        // TODO handle chyba (popup? toast popup?)
      } finally {
        _state.update { it.copy(isSaving = false) }
      }
    }
  }
}
