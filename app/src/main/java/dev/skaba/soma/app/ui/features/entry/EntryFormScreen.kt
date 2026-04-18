package dev.skaba.soma.app.ui.features.entry

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.skaba.soma.app.R
import dev.skaba.soma.app.ui.components.hints.LoadingFiller
import dev.skaba.soma.app.ui.components.scaffold.SomaTextOnlyAppBar
import dev.skaba.soma.app.ui.features.entry.components.LogEntryForm
import dev.skaba.soma.app.ui.features.entry.viewmodel.EntryFormViewModel

@Composable
fun EntryFormScreen(
  viewModel: EntryFormViewModel,
  navigateToLog: () -> Unit,
) {
  val state by viewModel.state.collectAsState()
  val scrollState = rememberScrollState()

  Scaffold(
    topBar = {
      SomaTextOnlyAppBar(
        text = if (state.isEditMode) stringResource(R.string.title_edit_log) else stringResource(R.string.title_log_food),
        onNavigateBack = navigateToLog,
      )
    },
    contentWindowInsets = WindowInsets(0.dp),
  ) { paddingValues ->
    if (state.isLoading) {
      LoadingFiller(modifier = Modifier.padding(paddingValues))
    } else {
      val spacing = 16.dp
      Column(
        modifier = Modifier
          .padding(paddingValues)
          .padding(spacing)
          .fillMaxWidth()
          .verticalScroll(scrollState),
      ) {
        LogEntryForm(
          state = state,
          onEvent = viewModel::onEvent,
          onSuccess = navigateToLog,
        )
      }
    }
  }
}
