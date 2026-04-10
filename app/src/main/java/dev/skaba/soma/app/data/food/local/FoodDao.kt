package dev.skaba.soma.app.data.food.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FoodDao {
  @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
  suspend fun insert(food: FoodEntity)

  @Query("SELECT * FROM foods WHERE id = :foodId")
  suspend fun getById(foodId: String): FoodEntity?

  @Query("SELECT * FROM foods WHERE name LIKE '%' || :name || '%'")
  suspend fun getAllByName(name: String): List<FoodEntity>

  @Query("DELETE FROM foods WHERE id = :foodId")
  suspend fun deleteById(foodId: String)
}