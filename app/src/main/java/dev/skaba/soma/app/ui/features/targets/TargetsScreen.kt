package dev.skaba.soma.app.ui.features.targets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.skaba.soma.app.ui.components.scaffold.SomaTextOnlyAppBar
import dev.skaba.soma.app.ui.features.targets.components.TargetsForm
import dev.skaba.soma.app.ui.features.targets.viewmodel.TargetsFormEvent
import dev.skaba.soma.app.ui.features.targets.viewmodel.TargetsFormState
import dev.skaba.soma.app.ui.features.targets.viewmodel.TargetsViewModel
import dev.skaba.soma.app.ui.theme.SOMATheme

@Composable
fun TargetsScreen(
  viewModel: TargetsViewModel,
  navigateBack: () -> Unit,
) {
  val state by viewModel.state.collectAsState()
  TargetsScreenContent(
    state = state,
    onEvent = { event -> viewModel.onEvent(event) },
    navigateBack = navigateBack,
  )
}

@Composable
fun TargetsScreenContent(
  state: TargetsFormState,
  onEvent: (TargetsFormEvent) -> Unit,
  navigateBack: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Scaffold(
    topBar = {
      SomaTextOnlyAppBar(
        text = "Daily targets",
        onNavigateBack = navigateBack,
      )
    },
  ) { paddingValues ->
    val spacing = 16.dp
    Column(
      modifier = Modifier
        .padding(paddingValues)
        .padding(spacing),
    ) {
      TargetsForm(
        state = state,
        onEvent = onEvent,
        onSuccess = navigateBack
      )
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
    )
  }
}