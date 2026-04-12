package dev.skaba.soma.app.ui.features.targets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.skaba.soma.app.R
import dev.skaba.soma.app.ui.components.scaffold.SomaTextOnlyAppBar
import dev.skaba.soma.app.ui.features.targets.components.ReminderSetting
import dev.skaba.soma.app.ui.features.targets.components.TargetsForm
import dev.skaba.soma.app.ui.features.targets.viewmodel.TargetsFormEvent
import dev.skaba.soma.app.ui.features.targets.viewmodel.TargetsFormState
import dev.skaba.soma.app.ui.features.targets.viewmodel.TargetsViewModel
import dev.skaba.soma.app.ui.theme.SOMATheme

@Composable
fun TargetsScreen(
  viewModel: TargetsViewModel,
  navigateBack: () -> Unit,
  navigateToLogScreen: () -> Unit,
) {
  val state by viewModel.state.collectAsStateWithLifecycle()
  TargetsScreenContent(
    state = state,
    onEvent = { event -> viewModel.onEvent(event) },
    navigateBack = navigateBack,
    navigateToLogScreen = navigateToLogScreen,
  )
}

@Composable
fun TargetsScreenContent(
  state: TargetsFormState,
  onEvent: (TargetsFormEvent) -> Unit,
  navigateBack: () -> Unit,
  navigateToLogScreen: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Scaffold(
    topBar = {
      SomaTextOnlyAppBar(
        text = stringResource(R.string.title_daily_targets),
        onNavigateBack = navigateBack,
      )
    },
    contentWindowInsets = WindowInsets(0.dp), // ignorovat inset od vnejsiho scaffoldu
  ) { paddingValues ->
    val spacing = 16.dp
    Column(
      verticalArrangement = Arrangement.spacedBy(spacing),
      modifier = Modifier
        .padding(paddingValues)
        .padding(spacing),
    ) {
      TargetsForm(
        state = state,
        onEvent = onEvent,
        onSuccess = navigateToLogScreen,
      )
      ReminderSetting()
    }
  }
}

@Preview(showBackground = true)
@Composable
private fun TargetsScreenPreview() {
  SOMATheme {
    TargetsScreenContent(
      state = TargetsFormState(),
      onEvent = {},
      navigateBack = {},
      navigateToLogScreen = {},
    )
  }
}