package dev.skaba.soma.app.data

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.skaba.soma.app.data.food.local.FoodDao
import dev.skaba.soma.app.data.food.local.FoodEntity
import dev.skaba.soma.app.data.log_entry.local.LogEntryDao
import dev.skaba.soma.app.data.log_entry.local.LogEntryEntity
import dev.skaba.soma.app.data.targets.local.TargetsDao
import dev.skaba.soma.app.data.targets.local.TargetsEntity

@Database(
  entities = [
    FoodEntity::class,
    TargetsEntity::class,
    LogEntryEntity::class,
  ],
  version = 1,
  exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {
  abstract fun foodDao(): FoodDao
  abstract fun targetsDao(): TargetsDao
  abstract fun logEntryDao(): LogEntryDao
}
