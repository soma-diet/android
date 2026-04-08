package dev.skaba.soma.app.data.food

import dev.skaba.soma.app.domain.food.Food
import dev.skaba.soma.app.domain.food.toEntity

class FoodRepositoryImpl(
    private val foodDao: FoodDao
) {
    override suspend fun insert(food: Food) {
        val entity = food.toEntity()
        foodDao.insert(entity)
    }
}