package dev.skaba.soma.app.ui.features.search.viewmodel

import androidx.lifecycle.ViewModel
import dev.skaba.soma.app.domain.food.FoodRepository
import dev.skaba.soma.app.ui.features.search.model.SearchFilter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SearchViewModel(
  foodRepository: FoodRepository
) : ViewModel() {
  private val _state = MutableStateFlow(SearchState())
  val state: StateFlow<SearchState> = _state.asStateFlow()

  /* #region updates */

  fun updateQuery(newQuery: String) {
    _state.value = _state.value.copy(query = newQuery)
  }

  fun updateFilter(newFilter: SearchFilter) {
    _state.value = _state.value.copy(filter = newFilter)
  }

  // fun updateFoods(newFoods: List<Food>) {
  //   _state.value = _state.value.copy(foods = newFoods)
  // }

  /* #endregion */
}