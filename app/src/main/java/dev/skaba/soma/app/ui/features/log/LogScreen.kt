package dev.skaba.soma.app.ui.features.log

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.skaba.soma.app.ui.components.list.SomaItemList
import dev.skaba.soma.app.ui.components.list.SomaItemListEntryData
import dev.skaba.soma.app.ui.features.log.components.progress.ProgressOverview
import dev.skaba.soma.app.ui.features.log.components.scaffold.LogScreenTopBar
import dev.skaba.soma.app.ui.theme.SOMATheme

@Composable
fun LogScreen() {
  val spacing = 16.dp
  val sampleEntries = listOf(
    SomaItemListEntryData(
      name = "Sample entry 1",
      subtext = "2x serving",
      sidetext = "300 kcal",
      onDelete = { },
      onEdit = { }
    ), SomaItemListEntryData(
      name = "Sample entry 2",
      subtext = "1x whole",
      sidetext = "1445 kcal",
      onDelete = { },
      onEdit = { }
    ), SomaItemListEntryData(
      name = "Sample entry 3",
      subtext = "3x pieces",
      sidetext = "457 kcal",
      onDelete = { },
      onEdit = { }
    ))

  Scaffold(
    topBar = {
      LogScreenTopBar(
        onDayBackwards = {},
        onDayForwards = {},
      )
    },
  ) { paddingValues ->
    Column(modifier = Modifier.padding(paddingValues).padding(bottom = spacing)) {
      ProgressOverview(modifier = Modifier.padding(spacing))
      SomaItemList(
        items = sampleEntries
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
private fun LogScreenPreview() {
  SOMATheme {
    LogScreen()
  }
}