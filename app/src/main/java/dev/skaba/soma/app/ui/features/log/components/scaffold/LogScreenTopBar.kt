package dev.skaba.soma.app.ui.features.log.components.scaffold

import android.text.format.DateUtils
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import dev.skaba.soma.app.R
import dev.skaba.soma.app.ui.components.scaffold.SomaAppBar
import dev.skaba.soma.app.util.formatDate
import java.util.Date

@Composable
fun LogScreenTopBar(
  date: Date,
  onDayBackwards: () -> Unit,
  onDayForwards: () -> Unit,
) {
  val isToday = DateUtils.isToday(date.time)
  val titleText =
    if (isToday) "Today" else if (DateUtils.isToday(date.time + DateUtils.DAY_IN_MILLIS)) "Yesterday" else formatDate(
      date,
    )

  SomaAppBar(
    title = {
      Text(
        text = titleText,
        style = MaterialTheme.typography.headlineMedium,
      )
    },
    navigationIcon = {
      ChangeDayButton(
        icon = R.drawable.arrow_back,
        description = "Go back a day",
        onClick = onDayBackwards,
      )
    },
    actions = {
      if (!isToday) {
        ChangeDayButton(
          icon = R.drawable.arrow_forward,
          description = "Go forward a day",
          onClick = onDayForwards,
        )
      }
    },
  )
}

@Composable
private fun ChangeDayButton(
  icon: Int,
  description: String,
  onClick: () -> Unit,
) {
  IconButton(
    onClick = onClick,
  ) {
    Icon(
      painter = painterResource(icon),
      contentDescription = description,
    )
  }
}
