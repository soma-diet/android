package dev.skaba.soma.app.data

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.skaba.soma.app.data.food.local.FoodDao
import dev.skaba.soma.app.data.food.local.FoodEntity

@Database(
  entities = [
    FoodEntity::class, // TADY PRIDAVAT ENTITY
  ],
  version = 1,
  exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
  abstract fun foodDao(): FoodDao // TADY PRIDAVAT DAO
}