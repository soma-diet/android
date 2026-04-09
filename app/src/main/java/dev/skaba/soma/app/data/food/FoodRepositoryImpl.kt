package dev.skaba.soma.app.data.food

import dev.skaba.soma.app.data.food.local.FoodDao
import dev.skaba.soma.app.data.food.local.toDomain
import dev.skaba.soma.app.data.food.local.toEntity
import dev.skaba.soma.app.domain.food.Food
import dev.skaba.soma.app.domain.food.FoodRepository

class FoodRepositoryImpl(
  private val foodDao: FoodDao,
) : FoodRepository {
  override suspend fun insert(food: Food) {
    val entity = food.toEntity()
    foodDao.insert(entity)
  }

  override suspend fun getAllByName(name: String): List<Food> {
    return foodDao.getAllByName(name).map { it.toDomain() }
  }
}