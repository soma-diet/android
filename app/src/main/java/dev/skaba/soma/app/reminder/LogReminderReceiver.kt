package dev.skaba.soma.app.reminder

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import dev.skaba.soma.app.MainActivity
import dev.skaba.soma.app.R

class LogReminderReceiver : BroadcastReceiver() {
  override fun onReceive(context: Context, intent: Intent?) {
    val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    setupNotificationChannel(manager)

    // akce po kliknuti
    val clickIntent = PendingIntent.getActivity(
      context,
      0,
      Intent(context, MainActivity::class.java),
      PendingIntent.FLAG_IMMUTABLE,
    )

    // vzhled notifikace
    val notification = NotificationCompat.Builder(context, "food_reminder_channel")
      .setSmallIcon(R.drawable.flatware_24px)
      .setContentTitle("Have you eaten yet?")
      .setContentText("Don't forget to log your food intake!")
      .setContentIntent(clickIntent)
      .setAutoCancel(true)
      .build()

    // poslani notifikace
    manager.notify(1, notification)
  }

  // nastaveni notification channelu podle verze androidu
  private fun setupNotificationChannel(manager: NotificationManager) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      val channel = NotificationChannel(
        "food_reminder_channel",
        "Food Reminders",
        NotificationManager.IMPORTANCE_DEFAULT,
      )
      manager.createNotificationChannel(channel)
    }
  }
}