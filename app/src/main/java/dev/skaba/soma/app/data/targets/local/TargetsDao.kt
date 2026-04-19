package dev.skaba.soma.app.data.targets.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TargetsDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertOrUpdate(targets: TargetsEntity)

  @Query("SELECT * FROM targets LIMIT 1")
  fun getTargets(): Flow<TargetsEntity>
}