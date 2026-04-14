package dev.skaba.soma.app.data.food.remote

import dev.skaba.soma.app.data.food.remote.dto.FoodResponseDto
import dev.skaba.soma.app.data.food.remote.dto.PagedFoodResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface FoodApi {
  @GET("api/foods")
  suspend fun getFoods(
    @Header("Authorization") authHeader: String
  ): FoodResponseDto

  @GET("api/foods/{id}")
  suspend fun getFoodById(
    @Path("id") id: String,
    @Header("Authorization") authHeader: String
  ): PagedFoodResponse
}