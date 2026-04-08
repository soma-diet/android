package dev.skaba.soma.app.data.food.remote.dto

import dev.skaba.soma.app.domain.food.Food
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FoodResponseDto(
  @SerialName("id") val id: String, // uuid
  @SerialName("name") val name: String,
  @SerialName("imageFilename") val imageFileName: String?,
  @SerialName("isMass") val isMass: Boolean,
  @SerialName("author") val author: String, // redundant
  @SerialName("isPrivate") val isPrivate: Boolean,
  @SerialName("macronutrients") val macronutrients: MacronutrientsDto,
  @SerialName("micronutrients") val micronutrients: MicronutrientsDto,
  @SerialName("type") val type: String,
  @SerialName("barcode") val barcode: String, // redundant
  @SerialName("brand") val brand: String,
  @SerialName("servings") val servings: List<ServingDto>,
)

@Serializable
data class FoodRequestDto(
  @SerialName("id") val id: String? = null,
  @SerialName("name") val name: String,
  @SerialName("barcode") val barcode: String? = null,
  @SerialName("brand") val brand: String? = null,
  @SerialName("isMass") val isMass: Boolean,
  @SerialName("macronutrients") val macronutrients: MacronutrientsDto,
  @SerialName("micronutrients") val micronutrients: MicronutrientsDto? = null,
  @SerialName("servings") val servings: List<ServingDto>? = null,
)

fun FoodResponseDto.toDomainModel(): Food {
  return Food(
    id = this.id,
    name = this.name,
    isMass = this.isMass,
    isPrivate = this.isPrivate,
    localImageUri = null,
    remoteImageUrl = this.imageFileName, // TODO rozsirit o full url, tohle je jen jmeno
    author = this.author,
    barcode = this.barcode,
    brand = this.brand,
    type = this.type,
    macronutrients = this.macronutrients.toDomainModel(),
    micronutrients = this.micronutrients.toDomainModel(),
    servings = this.servings.map { servingDto -> servingDto.toDomainModel() }
  )
}