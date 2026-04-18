package dev.skaba.soma.app.data.food

import dev.skaba.soma.app.auth.AuthRepository
import dev.skaba.soma.app.data.food.local.FoodDao
import dev.skaba.soma.app.data.food.local.toDomain
import dev.skaba.soma.app.data.food.local.toEntity
import dev.skaba.soma.app.data.food.remote.FoodApi
import dev.skaba.soma.app.data.food.remote.dto.toDomain
import dev.skaba.soma.app.domain.food.Food
import dev.skaba.soma.app.domain.food.FoodRepository
import dev.skaba.soma.app.ui.features.search.model.SearchFilter

class FoodRepositoryImpl(
  private val foodDao: FoodDao,
  private val foodApi: FoodApi,
  private val authRepository: AuthRepository,
) : FoodRepository {
  override suspend fun insert(food: Food) {
    val entity = food.toEntity()
    foodDao.insert(entity)
  }

  override suspend fun getById(foodId: String): Food? {
    return foodDao.getById(foodId)?.toDomain()
  }

  override suspend fun searchByName(name: String, filter: SearchFilter, page: Int): List<Food> {
    // get local (jen na page = 0, jinak by se nacitalo pri scrollovani)
    val localFoods = if (page == 0 && filter != SearchFilter.PUBLIC) {
      foodDao.getAllByName(name).map { it.toDomain() }
    } else emptyList()

    // get remote
    val remoteFoods = if (filter != SearchFilter.PRIVATE) {
      try {
        val token = authRepository.getAuthToken()
        if (token != null) {
          val response = foodApi.searchFoods("Bearer $token", name, page = page)
          response.content.map { it.toDomain() }
        } else {
          emptyList()
        }
      } catch (e: Exception) {
        e.printStackTrace()
        emptyList()
      }
    } else emptyList();

    // return mix
    return localFoods + remoteFoods
  }

  override suspend fun deleteById(foodId: String) {
    foodDao.deleteById(foodId)
  }
}
