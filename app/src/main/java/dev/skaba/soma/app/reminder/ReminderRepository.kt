package dev.skaba.soma.app.reminder

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "reminder_prefs")

class ReminderRepository(private val context: Context) {
  // klice v datastore
  private companion object {
    val REMINDER_HOUR = intPreferencesKey("reminder_hour")
    val REMINDER_MINUTE = intPreferencesKey("reminder_minute")
  }

  // flow na vraceni aktualne nastaveneho casu
  val reminderTimeFlow: Flow<Pair<Int, Int>> = context.dataStore.data.map { preferences ->
    val hour = preferences[REMINDER_HOUR] ?: 18
    val minute = preferences[REMINDER_MINUTE] ?: 0
    Pair(hour, minute)
  }

  // asynchronni ulozeni casu na disk
  suspend fun saveTime(hour: Int, minute: Int) {
    context.dataStore.edit { preferences ->
      preferences[REMINDER_HOUR] = hour
      preferences[REMINDER_MINUTE] = minute
    }
  }
}