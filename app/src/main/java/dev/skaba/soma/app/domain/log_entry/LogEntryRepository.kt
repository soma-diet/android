package dev.skaba.soma.app.domain.log_entry

import kotlinx.coroutines.flow.Flow
import java.util.Date

interface LogEntryRepository {
  suspend fun insert(logEntry: LogEntry)
  suspend fun deleteById(id: String)
  fun getEntriesByDate(date: Date): Flow<List<LogEntry>>
  suspend fun getById(id: String): LogEntry?
}
