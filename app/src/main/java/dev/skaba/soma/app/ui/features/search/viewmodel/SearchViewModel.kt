package dev.skaba.soma.app.ui.features.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.skaba.soma.app.domain.food.FoodRepository
import dev.skaba.soma.app.ui.features.search.model.SearchFilter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel(
  private val foodRepository: FoodRepository,
) : ViewModel() {
  private val _state = MutableStateFlow(SearchState())
  val state: StateFlow<SearchState> = _state.asStateFlow()

  init {
    search(_state.value.query, _state.value.filter)
  }

  fun onEvent(event: SearchEvent) {
    when (event) {
      is SearchEvent.QueryChanged -> {
        _state.value = _state.value.copy(query = event.query)
        search(event.query, _state.value.filter)
      }

      is SearchEvent.FilterChanged -> {
        _state.value = _state.value.copy(filter = event.filter)
        search(_state.value.query, event.filter)
      }

      is SearchEvent.DeleteFood -> {
        viewModelScope.launch {
          foodRepository.deleteById(event.id)
          search(_state.value.query, _state.value.filter) // aktulizovat data
        }
      }
    }
  }

  private fun search(query: String, filter: SearchFilter) {
    viewModelScope.launch {
      val foods = foodRepository.searchByName(query, filter)
      _state.value = _state.value.copy(foods = foods)
    }
  }
}