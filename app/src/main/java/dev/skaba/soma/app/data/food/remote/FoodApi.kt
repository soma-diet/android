package dev.skaba.soma.app.data.food.remote

import dev.skaba.soma.app.data.food.remote.dto.FoodResponseDto
import dev.skaba.soma.app.data.food.remote.dto.PagedFoodResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface FoodApi {
  @GET("api/foods")
  suspend fun searchFoods(
    @Header("Authorization") authHeader: String,
    @Query("query") name: String? = null,
  ): PagedFoodResponse

  @GET("api/foods/{id}")
  suspend fun getFoodById(
    @Path("id") id: String,
    @Header("Authorization") authHeader: String,
  ): FoodResponseDto
}