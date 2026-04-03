package dev.skaba.soma.app.ui.features.log.components.list

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dev.skaba.soma.app.ui.theme.SOMATheme

@Composable
fun LogEntriesList() {
  LazyColumn(

  ) {

  }
}

@Preview(showBackground=true)
@Composable
private fun LogEntriesListPreview() {
  SOMATheme {
    LogEntriesList()
  }
}