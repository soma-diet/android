package dev.skaba.soma.app.domain.food

interface FoodRepository {
    suspend fun insert(food: Food)
    suspend fun getAllByName(name: String): List<Food>
}