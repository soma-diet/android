package dev.skaba.soma.app.domain.food

import dev.skaba.soma.app.ui.features.search.model.SearchFilter

interface FoodRepository {
  suspend fun insert(food: Food)
  suspend fun getById(foodId: String): Food?
  suspend fun searchByName(name: String, filter: SearchFilter = SearchFilter.ALL, page: Int = 0): List<Food>
  suspend fun deleteById(foodId: String)
}