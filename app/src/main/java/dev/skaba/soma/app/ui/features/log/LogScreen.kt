package dev.skaba.soma.app.ui.features.log

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.skaba.soma.app.R
import dev.skaba.soma.app.ui.components.boxes.NoDataBox
import dev.skaba.soma.app.ui.components.list.SomaItemList
import dev.skaba.soma.app.ui.components.list.SomaItemListEntryData
import dev.skaba.soma.app.ui.features.log.components.progress.ProgressOverview
import dev.skaba.soma.app.ui.features.log.components.scaffold.LogScreenTopBar
import dev.skaba.soma.app.ui.features.log.viewmodel.LogEvent
import dev.skaba.soma.app.ui.features.log.viewmodel.LogState
import dev.skaba.soma.app.ui.features.log.viewmodel.LogViewModel
import dev.skaba.soma.app.ui.theme.SOMATheme

@Composable
fun LogScreen(
  viewModel: LogViewModel,
  onEditEntry: (String) -> Unit,
) {
  val state by viewModel.state.collectAsState()

  LogScreenContent(
    state = state,
    onEvent = viewModel::onEvent,
    onEditEntry = onEditEntry,
  )
}

@Composable
fun LogScreenContent(
  state: LogState,
  onEvent: (LogEvent) -> Unit,
  onEditEntry: (String) -> Unit,
) {
  val spacing = 16.dp
  val logItems = state.entries.map { entry ->
    SomaItemListEntryData(
      id = entry.id,
      name = entry.food.name,
      subtext = "${entry.quantity}x ${entry.serving?.name ?: "g"}",
      sidetext = "${entry.totalMacronutrients.kcal.toInt()} kcal",
      onDelete = { onEvent(LogEvent.DeleteEntry(entry.id)) },
      onEdit = { onEditEntry(entry.id) },
      onClick = null,
    )
  }

  Scaffold(
    topBar = {
      LogScreenTopBar(
        date = state.date,
        onDayBackwards = { onEvent(LogEvent.MoveToPreviousDay) },
        onDayForwards = { onEvent(LogEvent.MoveToNextDay) },
      )
    },
    contentWindowInsets = WindowInsets(0.dp),
  ) { paddingValues ->
    Column(
      modifier = Modifier
        .padding(paddingValues)
        .padding(bottom = spacing),
    ) {
      ProgressOverview(
        entries = state.entries,
        targets = state.targets,
        modifier = Modifier.padding(spacing),
      )

      if (state.entries.isNotEmpty()) {
        SomaItemList(
          items = logItems,
        )
      } else {
        NoDataBox(
          modifier=Modifier.padding(horizontal = spacing)
        ) {
          Icon(
            painter = painterResource(R.drawable.flatware_24px),
            contentDescription= stringResource(R.string.content_desc_flatware),
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(80.dp)
          )
          Text(
            text = stringResource(R.string.msg_have_you_eaten),
            style = MaterialTheme.typography.labelLarge,
          )
        }
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
private fun LogScreenPreview() {
  SOMATheme {
    LogScreenContent(
      state = LogState(),
      onEvent = {},
      onEditEntry = {},
    )
  }
}
