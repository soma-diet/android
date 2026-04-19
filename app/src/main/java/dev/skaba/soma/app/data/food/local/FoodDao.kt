package dev.skaba.soma.app.data.food.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

@Dao
interface FoodDao {
  @Insert(onConflict = OnConflictStrategy.IGNORE)
  suspend fun insert(food: FoodEntity): Long

  @Update
  suspend fun update(food: FoodEntity)

  @Transaction
  suspend fun upsert(food: FoodEntity) {
    val id = insert(food)
    if (id == -1L) {
      update(food)
    }
  }

  @Query("SELECT * FROM foods WHERE id = :foodId")
  suspend fun getById(foodId: String): FoodEntity?

  @Query("SELECT * FROM foods WHERE name LIKE '%' || :name || '%' AND isPrivate = 1")
  suspend fun getAllByName(name: String): List<FoodEntity>

  @Query("DELETE FROM foods WHERE id = :foodId")
  suspend fun deleteById(foodId: String)
}