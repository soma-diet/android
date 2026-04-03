package dev.skaba.soma.app.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "foods")
data class FoodEntity(
  @PrimaryKey val id: String,
  val name: String,
  val isMass: Boolean,
  val isPrivate: Boolean,

  val imageFileName: String?,
  val author: String?,
  val barcode: String?,
  val brand: String?,

  val type: String,

  // @Embedded = rozbalit objekt
  @Embedded(prefix = "macro_") val macronutrients: MacronutrientsEntity,
  @Embedded(prefix = "micro_") val micronutrients: MicronutrientsEntity?,

  // ulozeno jako json text
  val servingsJson: String,

  val isSynced: Boolean
)
