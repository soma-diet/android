package dev.skaba.soma.app.domain.model

data class Food(
  val id: String,
  val name: String,
  val isMass: Boolean,
  val isPrivate: Boolean,

  // odkazy na obrazky na backendu
  val images: FoodImages? = null,

  val author: String? = null,
  val barcode: String? = null,
  val brand: String? = null,

  val type: String = "FOOD", // redundant property

  val macronutrients: Macronutrients,
  val micronutrients: Micronutrients? = null,

  val servings: List<Serving> = emptyList()
)

data class FoodImages(
  val smallUrl: String,
  val largeUrl: String
)