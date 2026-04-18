package dev.skaba.soma.app.data.log_entry

import dev.skaba.soma.app.data.log_entry.local.LogEntryDao
import dev.skaba.soma.app.data.log_entry.local.toDomain
import dev.skaba.soma.app.data.log_entry.local.toEntity
import dev.skaba.soma.app.domain.food.FoodRepository
import dev.skaba.soma.app.domain.log_entry.LogEntry
import dev.skaba.soma.app.domain.log_entry.LogEntryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Calendar
import java.util.Date

class LogEntryRepositoryImpl(
  private val logEntryDao: LogEntryDao,
  private val foodRepository: FoodRepository,
) : LogEntryRepository {

  override suspend fun insert(logEntry: LogEntry) {
    foodRepository.insert(logEntry.food)
    logEntryDao.insert(logEntry.toEntity())
  }

  override suspend fun deleteById(id: String) {
    logEntryDao.deleteById(id)
  }

  override fun getEntriesByDate(date: Date): Flow<List<LogEntry>> {
    // postavit epoch milivteriny z date instance
    val calendar = Calendar.getInstance()
    calendar.time = date
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    val startTime = calendar.timeInMillis

    calendar.add(Calendar.DAY_OF_YEAR, 1)
    val endTime = calendar.timeInMillis

    // najit entries za den
    return logEntryDao.getEntriesByRange(startTime, endTime).map { list ->
      list.map { it.toDomain() }
    }
  }

  override suspend fun getById(id: String): LogEntry? {
    return logEntryDao.getById(id)?.toDomain()
  }
}
