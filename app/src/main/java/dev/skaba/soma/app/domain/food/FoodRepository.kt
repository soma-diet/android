package dev.skaba.soma.app.domain.food

interface FoodRepository {
  suspend fun insert(food: Food)
  suspend fun getById(foodId: String): Food?
  suspend fun getAllByName(name: String): List<Food>
  suspend fun deleteById(foodId: String)
}