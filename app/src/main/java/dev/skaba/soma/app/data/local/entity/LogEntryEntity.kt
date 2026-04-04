package dev.skaba.soma.app.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

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