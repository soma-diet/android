package dev.skaba.soma.app.ui.features.search.viewmodel

import dev.skaba.soma.app.domain.food.Food
import dev.skaba.soma.app.ui.features.search.model.SearchFilter

data class SearchState(
  val foods: List<Food> = emptyList(),
  val query: String = "",
  val filter: SearchFilter = SearchFilter.ALL,
  val currentPage: Int = 0,
  val isLastPage: Boolean = false,
  val isLoading: Boolean = false,
)
