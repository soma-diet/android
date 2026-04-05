package dev.skaba.soma.app.ui.features.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.skaba.soma.app.sample.FoodPreviewData
import dev.skaba.soma.app.ui.components.list.SomaItemList
import dev.skaba.soma.app.ui.components.list.SomaItemListEntryData
import dev.skaba.soma.app.ui.components.scaffold.SomaTextOnlyAppBar
import dev.skaba.soma.app.ui.features.search.components.SearchField
import dev.skaba.soma.app.ui.theme.SOMATheme

@Composable
fun SearchScreen() {
  val filters = listOf("All", "Private", "Public")
  val spacing = 16.dp

  val sampleSearchEntries = FoodPreviewData.allSamples.map { food ->
    SomaItemListEntryData(
      name = food.name,
      subtext = food.brand,
      sidetext = "150 kcal",
      onDelete = { },
      onEdit = { }
    )
  }

  Scaffold(
    topBar = {
      SomaTextOnlyAppBar(
        text = "Food search"
      )
    }
  ) { paddingValues ->
    Column(
      modifier = Modifier
        .padding(paddingValues)
        .padding(bottom = spacing)
    ) {
      SearchField(
        filters = filters,
        selectedFilter = "Private",
        onQueryChanged = { newQuery ->
          // TODO search query
        },
        onFilterChanged = {
          // TODO filter query
        },
        modifier = Modifier.padding(spacing)
      )
      SomaItemList(
        items = sampleSearchEntries
      )
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