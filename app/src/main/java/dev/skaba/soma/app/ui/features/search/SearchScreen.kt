package dev.skaba.soma.app.ui.features.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.skaba.soma.app.sample.FoodPreviewData
import dev.skaba.soma.app.ui.features.search.components.results.SearchResultsList
import dev.skaba.soma.app.ui.features.search.components.scaffold.SearchScreenTopBar
import dev.skaba.soma.app.ui.theme.SOMATheme

@Composable
fun SearchScreen() {
  Scaffold(
    topBar = {
      SearchScreenTopBar()
    }
  ) { paddingValues ->
    Column(
      modifier = Modifier.padding(paddingValues)
    ) {
      SearchResultsList(FoodPreviewData.allSamples)
    }
  }
}

@Preview(showBackground = true)
@Composable
private fun SearchScreenPreview() {
  SOMATheme {
    SearchScreen()
  }
}