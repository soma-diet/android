package dev.skaba.soma.app.domain.food

interface FoodRepository {
    suspend fun insertFood(food: Food)
}