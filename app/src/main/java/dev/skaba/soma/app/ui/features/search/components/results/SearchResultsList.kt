package dev.skaba.soma.app.ui.features.search.components.results

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.skaba.soma.app.domain.model.Food
import dev.skaba.soma.app.sample.FoodPreviewData
import dev.skaba.soma.app.ui.theme.SOMATheme

@Composable
fun SearchResultsList(
  searchResults: List<Food>,
  modifier: Modifier = Modifier
) {
  LazyColumn(

  ) {
    items(searchResults) { food ->
      SearchResultsItem(food)
    }
  }
}

@Composable
fun SearchResultsItem(
  item: Food,
  modifier: Modifier = Modifier
) {
  Row(

  ) {
    Text(item.name)
    Text(item.author.toString())
  }
}

@Preview(showBackground = true)
@Composable
private fun SearchResultsListPreview() {
  SOMATheme {
    SearchResultsList(FoodPreviewData.allSamples)
  }
}