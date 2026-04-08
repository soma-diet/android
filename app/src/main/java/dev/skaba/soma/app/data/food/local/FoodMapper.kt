package dev.skaba.soma.app.data.food.local

import dev.skaba.soma.app.domain.food.Food
import kotlinx.serialization.json.Json

fun Food.toEntity(): FoodEntity {
  return FoodEntity(
    id = this.id,
    name = this.name,
    isMass = this.isMass,
    isPrivate = this.isPrivate,

    localImagePath = this.localImageUri,
    remoteImagePath = this.remoteImageUrl,

    author = this.author,
    barcode = this.barcode,
    brand = this.brand,
    type = this.type,
    macronutrients = this.macronutrients.toEntity(),
    micronutrients = this.micronutrients?.toEntity(),
    servingsJson = Json.encodeToString(this.servings),
    isSynced = false // nova entita
  )
}

