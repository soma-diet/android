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

  fun updateKcal(kcal: Float?) {
    _state.update { it.copy(kcal = kcal) }
  }
}