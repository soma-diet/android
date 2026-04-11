package dev.skaba.soma.app.data.log_entry.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface LogEntryDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(logEntry: LogEntryEntity)

  @Query("DELETE FROM log_entries WHERE id = :id")
  suspend fun deleteById(id: String)

  @Transaction
  @Query("SELECT * FROM log_entries WHERE timestamp >= :startTime AND timestamp < :endTime ORDER BY timestamp DESC")
  fun getEntriesByRange(startTime: Long, endTime: Long): Flow<List<LogEntryWithFood>>

  @Transaction
  @Query("SELECT * FROM log_entries WHERE id = :id")
  suspend fun getById(id: String): LogEntryWithFood?
}
