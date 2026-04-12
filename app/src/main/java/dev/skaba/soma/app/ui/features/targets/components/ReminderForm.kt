package dev.skaba.soma.app.ui.features.targets.components

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import dev.skaba.soma.app.R
import dev.skaba.soma.app.reminder.ReminderRepository
import dev.skaba.soma.app.reminder.ReminderScheduler
import dev.skaba.soma.app.ui.components.buttons.PrimaryButton
import dev.skaba.soma.app.ui.components.buttons.SecondaryButton
import dev.skaba.soma.app.ui.components.forms.FormSection
import dev.skaba.soma.app.ui.theme.SOMATheme
import kotlinx.coroutines.launch
import java.util.Calendar

@SuppressLint("DefaultLocale")
@Composable
fun ReminderForm(modifier: Modifier = Modifier) {
  val context = LocalContext.current
  val coroutineScope = rememberCoroutineScope()
  val reminderRepo = remember { ReminderRepository(context) }

  // nacteni casu notifikace pres flow
  val savedTime by reminderRepo.reminderTimeFlow.collectAsState(initial = Pair(18, 0))

  var hasNotificationPermission by remember {
    mutableStateOf(
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        ContextCompat.checkSelfPermission(
          context,
          Manifest.permission.POST_NOTIFICATIONS,
        ) == PackageManager.PERMISSION_GRANTED
      } else {
        true
      },
    )
  }

  val permissionLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.RequestPermission(),
  ) { isGranted ->
    hasNotificationPermission = isGranted
  }

  val showTimePicker = remember { mutableStateOf(false) }
  val showPermissionRationale = remember { mutableStateOf(false) }

  FormSection(
    title = "Notifications",
    modifier = modifier,
  ) {
    if (!hasNotificationPermission) {
      PrimaryButton(
        text = stringResource(R.string.label_allow_notifications),
        onClick = {
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            showPermissionRationale.value = true
          }
        },
        modifier = Modifier.fillMaxWidth(),
      )
    } else {
      // formatovany cas pro tlacitko
      val formattedTime = String.format("%02d:%02d", savedTime.first, savedTime.second)

      SecondaryButton(
        text = "Change reminder time ($formattedTime)",
        onClick = { showTimePicker.value = true },
        modifier = Modifier.fillMaxWidth(),
      )
    }

    if (showPermissionRationale.value) {
      RationaleDialog(
        onDismiss = { showPermissionRationale.value = false },
        onConfirm = {
          showPermissionRationale.value = false
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
          }
        },
      )
    }

    if (showTimePicker.value) {
      TimePickerDialog(
        initialHour = savedTime.first,
        initialMinute = savedTime.second,
        onDismiss = { showTimePicker.value = false },
        onSave = { hour, minute ->
          showTimePicker.value = false

          // ulozit asynchronne do datastore
          coroutineScope.launch {
            reminderRepo.saveTime(hour, minute)
          }

          val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
          }

          // pokud je cas v minulosti tak se posune az na zitrejsi den
          if (calendar.timeInMillis <= System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1)
          }

          // nastaveni reminderu (scheduler)
          val scheduler = ReminderScheduler(context)
          scheduler.scheduleDailyReminder(calendar.timeInMillis)
        },
      )
    }
  }
}

@Composable
fun RationaleDialog(
  onDismiss: () -> Unit,
  onConfirm: () -> Unit,
) {
  AlertDialog(
    containerColor = MaterialTheme.colorScheme.surface,
    titleContentColor = MaterialTheme.colorScheme.onSurface,
    textContentColor = MaterialTheme.colorScheme.onSurface,

    onDismissRequest = onDismiss,
    title = { Text(stringResource(R.string.notification_permission_title)) },
    text = { Text(stringResource(R.string.notification_permission_rationale)) },
    confirmButton = {
      PrimaryButton(
        text = stringResource(R.string.label_ok),
        onClick = onConfirm,
      )
    },
    dismissButton = {
      SecondaryButton(
        text = stringResource(R.string.label_cancel),
        onClick = onDismiss,
      )
    },
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
  initialHour: Int,
  initialMinute: Int,
  onDismiss: () -> Unit,
  onSave: (Int, Int) -> Unit,
) {
  val timePickerState = rememberTimePickerState(
    initialHour = initialHour,
    initialMinute = initialMinute,
    is24Hour = true,
  )

  AlertDialog(
    containerColor = MaterialTheme.colorScheme.surface,
    titleContentColor = MaterialTheme.colorScheme.onSurface,
    textContentColor = MaterialTheme.colorScheme.onSurface,

    onDismissRequest = onDismiss,
    confirmButton = {
      PrimaryButton(
        text = stringResource(R.string.label_save),
        onClick = {
          onSave(timePickerState.hour, timePickerState.minute)
        },
      )
    },
    dismissButton = {
      SecondaryButton(
        text = stringResource(R.string.label_cancel),
        onClick = onDismiss,
      )
    },
    text = {
      Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {
        TimePicker(
          state = timePickerState,
          colors = TimePickerDefaults.colors().copy(
            // cifernik
            selectorColor = MaterialTheme.colorScheme.primary,
            clockDialColor = MaterialTheme.colorScheme.surfaceVariant,

            // text na ciferniku
            clockDialUnselectedContentColor = MaterialTheme.colorScheme.onSurface,
            clockDialSelectedContentColor = MaterialTheme.colorScheme.onPrimary,

            // box nahore s hodinama
            timeSelectorSelectedContainerColor = MaterialTheme.colorScheme.primary,
            timeSelectorSelectedContentColor = MaterialTheme.colorScheme.onPrimary,

            // text v boxu nahore
            timeSelectorUnselectedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            timeSelectorUnselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
          ),
        )
      }
    },
  )
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ReminderFormPreview() {
  SOMATheme {
    ReminderForm()
  }
}
