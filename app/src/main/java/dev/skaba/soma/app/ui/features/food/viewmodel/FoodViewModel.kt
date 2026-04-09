package dev.skaba.soma.app.ui.features.food.viewmodel

import androidx.lifecycle.ViewModel
import dev.skaba.soma.app.domain.food.FoodRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FoodViewModel(
  private val repository: FoodRepository,
) : ViewModel() // klicova implementace ViewModel!!
{
  private val _state = MutableStateFlow(FoodFormState()) // private state
  val state: StateFlow<FoodFormState> = _state.asStateFlow() // exposed state

  fun updateName(newName: String) {
    _state.update { it.copy(name = newName) }
  }

  fun updateBrand(brand: String) {
    _state.update { it.copy(brand = brand) }
  }

  fun updateIsLiquid(isLiquid: Boolean) {
    _state.update { it.copy(isLiquid = isLiquid) }
  }

  fun updateKcal(kcal: Float?) {
    _state.update { it.copy(kcal = kcal) }
  }

  fun updateKcal(kcal: Int?) {
    _state.update { it.copy(energyKcal = kcal) }
  }

  fun updateCarbs(carbs: Float?) {
    _state.update { it.copy(carbohydrates = carbs) }
  }

  fun updateProteins(proteins: Float?) {
    _state.update { it.copy(proteins = proteins) }
  }

  fun updateFats(fats: Float?) {
    _state.update { it.copy(fats = fats) }
  }
}