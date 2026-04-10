package dev.skaba.soma.app.data.targets.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "targets")
data class TargetsEntity(
  @PrimaryKey val id: Int = 1, // je pouze jeden zaznam cilu v db
  val kcal: Float? = null,
  val carbs: Float? = null,
  val protein: Float? = null,
  val fats: Float? = null,
  val fiber: Float? = null,
  val sodium: Float? = null,
)
