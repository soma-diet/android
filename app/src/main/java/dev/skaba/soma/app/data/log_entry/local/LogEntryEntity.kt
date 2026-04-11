package dev.skaba.soma.app.data.log_entry.local

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import dev.skaba.soma.app.data.food.local.FoodEntity

@Entity(tableName = "log_entries")
data class LogEntryEntity(
  @PrimaryKey val id: String,
  val timestamp: Long, // epoch milivteriny

  val foodId: String,
  val servingId: String?, // null = 1g
  val quantity: Float,
)

data class LogEntryWithFood(
  @Embedded val entry: LogEntryEntity,
  @Relation(
    parentColumn = "foodId",
    entityColumn = "id",
  )
  val food: FoodEntity,
)
