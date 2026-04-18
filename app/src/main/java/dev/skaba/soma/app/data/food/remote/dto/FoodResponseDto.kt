package dev.skaba.soma.app.data.food.remote.dto

import dev.skaba.soma.app.BuildConfig
import dev.skaba.soma.app.domain.food.Food
import kotlinx.serialization.Serializable

const val IMAGES_URL = BuildConfig.BACKEND_URL + "/images"

@Serializable
data class FoodResponseDto(
  val id: String,
  val type: String? = null,
  val author: String? = null,
  val barcode: String? = null,
  val brand: String? = null,
  val imageFilename: String? = null,
  val isMass: Boolean,
  val isPrivate: Boolean,
  val macronutrients: MacronutrientsDto? = null,
  val micronutrients: MicronutrientsDto? = null,
  val name: String,
  val servings: List<ServingDto>
)

fun FoodResponseDto.toDomain(): Food {
  return Food(
    id = this.id,
    name = this.name,
    isMass = this.isMass,
    isPrivate = this.isPrivate,
    localImageUri = null,
    remoteImageUrl = if (this.imageFilename != null) IMAGES_URL + "/large_" + this.imageFilename else null,
    brand = this.brand,
    macronutrients = this.macronutrients?.toDomain() ?: dev.skaba.soma.app.domain.food.Macronutrients(0f, 0f, 0f, 0f),
    micronutrients = this.micronutrients?.toDomain() ?: dev.skaba.soma.app.domain.food.Micronutrients(null, null),
    servings = this.servings.map { servingDto -> servingDto.toDomain() }
  )
}