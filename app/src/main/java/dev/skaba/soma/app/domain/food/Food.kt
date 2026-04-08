package dev.skaba.soma.app.domain.food

import dev.skaba.soma.app.data.food.local.FoodEntity
import kotlinx.serialization.json.Json

data class Food(
  val id: String,
  val name: String,
  val isMass: Boolean,
  val isPrivate: Boolean,

  // odkazy na obrazky na backendu
  val localImageUri: String? = null,
  val remoteImageUrl: String? = null,

  val author: String? = null,
  val barcode: String? = null,
  val brand: String? = null,

  val type: String = "FOOD", // redundant property

  val macronutrients: Macronutrients,
  val micronutrients: Micronutrients? = null,

  val servings: List<Serving> = emptyList()
)

