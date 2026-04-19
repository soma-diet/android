package dev.skaba.soma.app.reminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent

class ReminderScheduler(private val context: Context) {
  fun scheduleDailyReminder(timeInMillis: Long) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, LogReminderReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(
      context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
    )

    alarmManager.setRepeating(
      AlarmManager.RTC_WAKEUP,
      timeInMillis,
      AlarmManager.INTERVAL_DAY,
      pendingIntent,
    )
  }
}