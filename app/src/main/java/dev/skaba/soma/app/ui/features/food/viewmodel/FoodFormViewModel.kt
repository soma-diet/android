package dev.skaba.soma.app.ui.features.food.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dev.skaba.soma.app.domain.food.Food
import dev.skaba.soma.app.domain.food.FoodRepository
import dev.skaba.soma.app.domain.food.Macronutrients
import dev.skaba.soma.app.domain.food.Micronutrients
import dev.skaba.soma.app.domain.food.Serving
import dev.skaba.soma.app.ui.data.validateNumberNotEmpty
import dev.skaba.soma.app.ui.data.validateNumberNotNegative
import dev.skaba.soma.app.ui.data.validateTextNotEmpty
import dev.skaba.soma.app.ui.navigation.FoodFormScreenRoute
import dev.skaba.soma.app.util.ImageProcessor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class FoodFormViewModel(
  savedStateHandle: SavedStateHandle,
  private val repository: FoodRepository,
  private val imageProcessor: ImageProcessor,
) : ViewModel() // klicova implementace ViewModel!!
{
  private val _state = MutableStateFlow(FoodFormState()) // private state
  val state: StateFlow<FoodFormState> = _state.asStateFlow() // exposed state

  // id jidla z cesty (pro editaci jidla)
  private val foodId: String? = savedStateHandle.toRoute<FoodFormScreenRoute>().foodId

  init {
    foodId?.let { id ->
      viewModelScope.launch {
        val food = repository.getById(id)
        food?.let { loadFoodIntoState(it) }
      }
    }
  }

  // nacist jidlo do state
  private fun loadFoodIntoState(food: Food) {
    _state.update {
      it.copy(
        name = it.name.copy(value = food.name),
        brand = it.brand.copy(value = food.brand ?: ""),
        isLiquid = it.isLiquid.copy(value = !food.isMass),
        localImageUri = it.localImageUri.copy(value = food.localImageUri),
        kcal = it.kcal.copy(value = food.macronutrients.kcal),
        carbs = it.carbs.copy(value = food.macronutrients.carbs),
        protein = it.protein.copy(value = food.macronutrients.protein),
        fats = it.fats.copy(value = food.macronutrients.fats),
        fiber = it.fiber.copy(value = food.micronutrients?.fiber),
        sodium = it.sodium.copy(value = food.micronutrients?.sodium),
        servings = food.servings.map { serving ->
          ServingState(id = serving.id).copy(
            name = ServingState().name.copy(value = serving.name),
            size = ServingState().size.copy(value = serving.size),
          )
        },
        isEditMode = true,
      )
    }
  }

  fun onEvent(event: FoodFormEvent) {
    when (event) {
      is FoodFormEvent.NameChanged -> updateName(event.name)
      is FoodFormEvent.BrandChanged -> updateBrand(event.brand)
      is FoodFormEvent.IsLiquidChanged -> updateIsLiquid(event.isLiquid)

      is FoodFormEvent.ImageChanged -> updateImageUri(event.imageUri)

      is FoodFormEvent.KcalChanged -> updateKcal(event.kcal)
      is FoodFormEvent.CarbsChanged -> updateCarbs(event.carbs)
      is FoodFormEvent.ProteinChanged -> updateProtein(event.protein)
      is FoodFormEvent.FatsChanged -> updateFats(event.fats)

      is FoodFormEvent.FiberChanged -> updateFiber(event.fiber)
      is FoodFormEvent.SodiumChanged -> updateSodium(event.sodium)

      FoodFormEvent.AddServing -> addServing()
      is FoodFormEvent.RemoveServing -> removeServing(event.servingId)
      is FoodFormEvent.ServingNameChanged -> updateServingName(event.servingId, event.newName)
      is FoodFormEvent.ServingSizeChanged -> updateServingSize(event.servingId, event.newSize)

      is FoodFormEvent.SaveFood -> saveFood(event.onSuccess)
    }
  }

  /* #region updates */

  private fun updateName(newName: String) {
    _state.update {
      it.copy(
        name = it.name.copy(
          value = newName,
          error = null, // pri zmene hodnoty se resetuje error na null
        ),
      )
    }
  }

  private fun updateBrand(newBrand: String) {
    _state.update {
      it.copy(
        brand = it.brand.copy(
          value = newBrand,
          error = null,
        ),
      )
    }
  }

  private fun updateIsLiquid(newIsLiquid: Boolean) {
    _state.update {
      it.copy(
        isLiquid = it.isLiquid.copy(
          value = newIsLiquid,
          error = null,
        ),
      )
    }
  }

  private fun updateImageUri(imageUri: String?) {
    if (imageUri == null) {
      _state.update {
        it.copy(
          localImageUri = it.localImageUri.copy(
            value = null,
            error = null,
          ),
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
            error = null,
          ),
        )
      }
    } else {
      _state.update {
        it.copy(
          localImageUri = it.localImageUri.copy(
            value = null,
            error = "Image size is too large (max 1MB)",
          ),
        )
      }
    }
  }

  private fun updateKcal(newKcal: Float?) {
    _state.update {
      it.copy(
        kcal = it.kcal.copy(
          value = newKcal,
          error = null,
        ),
      )
    }
  }

  private fun updateCarbs(newCarbs: Float?) {
    _state.update {
      it.copy(
        carbs = it.carbs.copy(
          value = newCarbs,
          error = null,
        ),
      )
    }
  }

  private fun updateProtein(newProteins: Float?) {
    _state.update {
      it.copy(
        protein = it.protein.copy(
          value = newProteins,
          error = null,
        ),
      )
    }
  }

  private fun updateFats(newFats: Float?) {
    _state.update {
      it.copy(
        fats = it.fats.copy(
          value = newFats,
          error = null,
        ),
      )
    }
  }

  private fun updateFiber(newFiber: Float?) {
    _state.update {
      it.copy(
        fiber = it.fiber.copy(
          value = newFiber,
          error = null,
        ),
      )
    }
  }

  private fun updateSodium(newSodium: Float?) {
    _state.update {
      it.copy(
        sodium = it.sodium.copy(
          value = newSodium,
          error = null,
        ),
      )
    }
  }

  /* #region servings */

  private fun addServing() {
    _state.update {
      it.copy(
        servings = it.servings + ServingState(),
      )
    }
  }

  private fun removeServing(servingId: String) {
    _state.update {
      it.copy(
        servings = it.servings.filter { serving -> serving.id != servingId },
      )
    }
  }

  private fun updateServingName(servingId: String, newName: String) {
    _state.update { current ->
      val updatedServings = current.servings.map { serving ->
        if (serving.id == servingId) {
          serving.copy(
            name = serving.name.copy(
              value = newName,
              error = null,
            ),
          )
        } else {
          serving
        }
      }

      current.copy(servings = updatedServings)
    }
  }

  private fun updateServingSize(servingId: String, newSize: Float?) {
    _state.update { current ->
      val updatedServings = current.servings.map { serving ->
        if (serving.id == servingId) {
          serving.copy(
            size = serving.size.copy(
              value = newSize,
              error = null,
            ),
          )
        } else {
          serving
        }
      }

      current.copy(servings = updatedServings)
    }
  }

  /* #endregion */
  /* #endregion */

  private fun saveFood(
    onSuccess: () -> Unit,
  ) {
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
        fiber = current.fiber.validateNumberNotNegative("Fiber cannot be negative"),
        sodium = current.sodium.validateNumberNotNegative("Sodium cannot be negative"),
        servings = current.servings.map { serving ->
          serving.copy(
            name = serving.name.validateTextNotEmpty("Serving name required"),
            size = serving.size.validateNumberNotEmpty("Size required")
              .validateNumberNotNegative("Size cannot be negative"),
          )
        },
      )
    }

    val validatedState = _state.value
    val hasNormalError = validatedState.name.error != null ||
        validatedState.kcal.error != null ||
        validatedState.carbs.error != null ||
        validatedState.protein.error != null ||
        validatedState.fats.error != null
    val hasServingError = validatedState.servings.any {
      it.name.error != null || it.size.error != null
    }

    if (hasNormalError || hasServingError) return

    _state.update { it.copy(isSaving = true) }

    viewModelScope.launch {
      try {
        // ziskani uri obrazku po ulozeni
        val currentFoodId = foodId ?: UUID.randomUUID().toString()
        val finalImageUri = validatedState.localImageUri.value?.let { uri ->
          imageProcessor.saveImgToStorage(uri, currentFoodId)
        }

        val newFood = Food(
          id = currentFoodId,
          name = validatedState.name.value,
          isMass = !validatedState.isLiquid.value,
          isPrivate = true,

          localImageUri = finalImageUri,
          remoteImageUrl = null,

          brand = validatedState.brand.value,

          servings = validatedState.servings.map { servingState ->
            Serving(
              id = servingState.id,
              name = servingState.name.value,
              size = servingState.size.value!!,
            )
          },

          macronutrients = Macronutrients(
            kcal = validatedState.kcal.value!!,
            carbs = validatedState.carbs.value!!,
            protein = validatedState.protein.value!!,
            fats = validatedState.fats.value!!,
          ),
          micronutrients = Micronutrients(
            fiber = validatedState.fiber.value,
            sodium = validatedState.sodium.value,
          ),
        )
        repository.insert(newFood)
        onSuccess()
      } catch (e: Exception) {
        e.printStackTrace()
      } finally {
        _state.update { it.copy(isSaving = false) }
      }
    }
  }
}