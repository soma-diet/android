package dev.skaba.soma.app.ui.features.food.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.skaba.soma.app.domain.food.Food
import dev.skaba.soma.app.domain.food.FoodRepository
import dev.skaba.soma.app.domain.food.Macronutrients
import dev.skaba.soma.app.domain.food.Micronutrients
import dev.skaba.soma.app.ui.data.validateNumberNotEmpty
import dev.skaba.soma.app.ui.data.validateNumberNotNegative
import dev.skaba.soma.app.ui.data.validateTextNotEmpty
import dev.skaba.soma.app.util.ImageProcessor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class FoodFormViewModel(
  private val repository: FoodRepository,
  private val imageProcessor: ImageProcessor,
) : ViewModel() // klicova implementace ViewModel!!
{
  private val _state = MutableStateFlow(FoodFormState()) // private state
  val state: StateFlow<FoodFormState> = _state.asStateFlow() // exposed state

  /* #region updates */

  fun updateName(newName: String) {
    _state.update {
      it.copy(
        name = it.name.copy(
          value = newName,
          error = null // pri zmene hodnoty se resetuje error na null
        )
      )
    }
  }

  fun updateBrand(newBrand: String) {
    _state.update {
      it.copy(
        brand = it.brand.copy(
          value = newBrand,
          error = null
        )
      )
    }
  }

  fun updateIsLiquid(newIsLiquid: Boolean) {
    _state.update {
      it.copy(
        isLiquid = it.isLiquid.copy(
          value = newIsLiquid,
          error = null
        )
      )
    }
  }

  fun updateImageUri(imageUri: String?) {
    if (imageUri == null) {
      _state.update {
        it.copy(
          localImageUri = it.localImageUri.copy(
            value = null,
            error = null
          )
        )
      }
      return
    }

    val isValid = imageProcessor.isImageSizeValid(imageUri, maxMb = 2)

    if (isValid) {
      _state.update {
        it.copy(
          localImageUri = it.localImageUri.copy(
            value = imageUri,
            error = null
          )
        )
      }
    } else {
      _state.update {
        it.copy(
          localImageUri = it.localImageUri.copy(
            value = null,
            error = "Image size is too large (max 1MB)"
          ),
        )
      }
    }
  }

  fun updateKcal(newKcal: Float?) {
    _state.update {
      it.copy(
        kcal = it.kcal.copy(
          value = newKcal,
          error = null
        )
      )
    }
  }

  fun updateCarbs(newCarbs: Float?) {
    _state.update {
      it.copy(
        carbs = it.carbs.copy(
          value = newCarbs,
          error = null
        )
      )
    }
  }

  fun updateProteins(newProteins: Float?) {
    _state.update {
      it.copy(
        protein = it.protein.copy(
          value = newProteins,
          error = null
        )
      )
    }
  }

  fun updateFats(newFats: Float?) {
    _state.update {
      it.copy(
        fats = it.fats.copy(
          value = newFats,
          error = null
        )
      )
    }
  }

  fun updateFiber(newFiber: Float?) {
    _state.update {
      it.copy(
        fiber = it.fiber.copy(
          value = newFiber,
          error = null
        )
      )
    }
  }

  fun updateSodium(newSodium: Float?) {
    _state.update {
      it.copy(
        sodium = it.sodium.copy(
          value = newSodium,
          error = null
        )
      )
    }
  }

  /* #endregion */

  fun saveFood() {
    _state.update { current ->
      current.copy(
        name = current.name.validateTextNotEmpty("Name is required"),
        kcal = current.kcal.validateNumberNotEmpty("Calories are required")
          .validateNumberNotNegative("Calories cannot be negative"),
        carbs = current.carbs.validateNumberNotEmpty("Carbs are required")
          .validateNumberNotNegative("Carbohydrates cannot be negative"),
        protein = current.protein.validateNumberNotEmpty("Protein is required")
          .validateNumberNotNegative("Protein cannot be negative"),
        fats = current.fats.validateNumberNotEmpty("Fats are required")
          .validateNumberNotNegative("Fats cannot be negative"),
      )
    }

    val validatedState = _state.value
    val hasError = validatedState.name.error != null ||
        validatedState.kcal.error != null ||
        validatedState.carbs.error != null ||
        validatedState.protein.error != null ||
        validatedState.fats.error != null

    if (hasError) return

    _state.update { it.copy(isSaving = true) }

    viewModelScope.launch {
      try {
        val newFood = Food(
          id = UUID.randomUUID().toString(),
          name = validatedState.name.value,
          isMass = !validatedState.isLiquid.value,
          isPrivate = true,

          localImageUri = validatedState.localImageUri.value,
          remoteImageUrl = null,

          brand = validatedState.brand.value,

          macronutrients = Macronutrients(
            kcal = validatedState.kcal.value!!,
            carbs = validatedState.carbs.value!!,
            protein = validatedState.protein.value!!,
            fats = validatedState.fats.value!!,
          ),
          micronutrients = Micronutrients(
            fiber = validatedState.fiber.value,
            sodium = validatedState.sodium.value,
          )
        )
        repository.insert(newFood)
      } catch (e: Exception) {
        // TODO handle chyba (popup? toast popup?)
      } finally {
        _state.update { it.copy(isSaving = false) }
      }
    }
  }
}