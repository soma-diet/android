package dev.skaba.soma.app.ui.features.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.skaba.soma.app.data.food.remote.FoodApi
import dev.skaba.soma.app.domain.food.FoodRepository

class SearchViewModelFactory(
  private val foodRepository: FoodRepository,
  private val foodApi: FoodApi,
) : ViewModelProvider.Factory {
  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
      return SearchViewModel(
        foodRepository = foodRepository,
        foodApi = foodApi,
      ) as T
    }
    throw IllegalArgumentException("Unknown ViewModel class")
  }
}