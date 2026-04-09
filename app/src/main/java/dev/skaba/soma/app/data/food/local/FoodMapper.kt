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

    brand = this.brand,
    macronutrients = this.macronutrients.toEntity(),
    micronutrients = this.micronutrients?.toEntity(),
    servingsJson = Json.encodeToString(this.servings),
    isSynced = false // nova entita
  )
}

fun FoodEntity.toDomain(): Food {
  return Food(
    id = this.id,
    name = this.name,
    isMass = this.isMass,
    isPrivate = this.isPrivate, // stejne by melo byt vzdy true, nemuze si ulozit cizi jidlo (nebo muze kvuli loggum? TODO)
    localImageUri = this.localImagePath,
    remoteImageUrl = this.remoteImagePath,
    brand = this.brand,
    macronutrients = this.macronutrients.toDomain(),
    micronutrients = this.micronutrients?.toDomain(),
    servings = Json.decodeFromString(this.servingsJson),
  )
}

