package dev.skaba.soma.app.ui.features.log

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.skaba.soma.app.ui.features.log.components.list.LogEntriesList
import dev.skaba.soma.app.ui.features.log.components.progress.ProgressOverview
import dev.skaba.soma.app.ui.features.log.components.scaffold.LogScreenTopBar
import dev.skaba.soma.app.ui.theme.SOMATheme

@Composable
fun LogScreen() {
  Scaffold(
    topBar = {
      LogScreenTopBar(
        onDayBackwards = {},
        onDayForwards = {},
      )
    },
  ) { paddingValues ->
    Column(modifier = Modifier.padding(paddingValues)) {
      ProgressOverview(modifier = Modifier.padding(12.dp))
      LogEntriesList()
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