package dev.skaba.soma.app.data.log.local

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import dev.skaba.soma.app.data.food.local.FoodEntity

@Entity(tableName = "diary_entries")
data class LogEntryEntity(
  @PrimaryKey val id: String,
  val timestamp: String,

  val foodId: String, // odkaz do foods tabulky

  val servingId: String?,
  val servingName: String?,
  val servingSize: Float?,
  val quantity: Float,

  val isSynced: Boolean = false
)

// spojeni -> vraceno pri hledani LogEntry
data class LogEntryWithFood(
  @Embedded val entry: LogEntryEntity,
  @Relation(
    parentColumn = "foodId",
    entityColumn = "id"
  )
  val food: FoodEntity
)