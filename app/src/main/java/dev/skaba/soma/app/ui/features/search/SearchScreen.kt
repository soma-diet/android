package dev.skaba.soma.app.ui.features.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.skaba.soma.app.R
import dev.skaba.soma.app.ui.components.boxes.ContentSurface
import dev.skaba.soma.app.ui.components.hints.LoadingFiller
import dev.skaba.soma.app.ui.components.list.SomaItemList
import dev.skaba.soma.app.ui.components.list.SomaItemListEntryData
import dev.skaba.soma.app.ui.components.scaffold.SomaActionButton
import dev.skaba.soma.app.ui.components.scaffold.SomaTextOnlyAppBar
import dev.skaba.soma.app.ui.features.search.components.SearchField
import dev.skaba.soma.app.ui.features.search.viewmodel.SearchEvent
import dev.skaba.soma.app.ui.features.search.viewmodel.SearchState
import dev.skaba.soma.app.ui.features.search.viewmodel.SearchViewModel

@Composable
fun SearchScreen(
  searchViewModel: SearchViewModel,
  navigateToNewFoodScreen: () -> Unit,
  navigateToEditScreen: (String?) -> Unit,
  onLogFood: (String) -> Unit,
) {
  val state by searchViewModel.state.collectAsState()
  SearchScreenContent(
    state = state,
    onEvent = { event -> searchViewModel.onEvent(event) },
    navigateToNewFoodScreen = navigateToNewFoodScreen,
    navigateToEditScreen = navigateToEditScreen,
    onLogFood = onLogFood,
  )
}

@Composable
fun SearchScreenContent(
  state: SearchState,
  onEvent: (SearchEvent) -> Unit,
  navigateToNewFoodScreen: () -> Unit,
  navigateToEditScreen: (String?) -> Unit,
  onLogFood: (String) -> Unit,
) {
  val selectedFilterInput = remember(state.filter) { mutableStateOf(state.filter) }

  val spacing = 16.dp
  Scaffold(
    topBar = {
      SomaTextOnlyAppBar(
        text = stringResource(R.string.title_food_search),
      )
    },
    floatingActionButton = {
      SomaActionButton(
        onClick = navigateToNewFoodScreen,
        modifier = Modifier.padding(bottom = spacing),
      ) {
        Icon(
          painter = painterResource(R.drawable.add_24px),
          contentDescription = stringResource(R.string.content_desc_add_food),
          modifier = Modifier.size(24.dp),
        )
      }
    },
    contentWindowInsets = WindowInsets(0.dp), // ignorovat inset od vnejsiho scaffoldu
  ) { paddingValues ->
    Column(
      modifier = Modifier
        .padding(paddingValues)
        .padding(bottom = spacing),
    ) {
      SearchField(
        selectedFilter = selectedFilterInput,
        onQueryChanged = { newQuery -> onEvent(SearchEvent.QueryChanged(newQuery)) },
        onFilterChanged = { newFilter -> onEvent(SearchEvent.FilterChanged(newFilter)) },
        modifier = Modifier.padding(spacing),
      )

      if (state.foods.isNotEmpty()) {
        SomaItemList(
          items = state.foods.map { food ->
            val onDelete = if (food.isPrivate) {
              { onEvent(SearchEvent.DeleteFood(food.id)) }
            } else {
              null
            }
            val onEdit = if (food.isPrivate) {
              {
                // onEvent(SearchEvent.EditFood(food.id))
                navigateToEditScreen(food.id)
              }
            } else {
              null
            }

            SomaItemListEntryData(
              id = food.id,
              name = food.name,
              subtext = food.brand,
              sidetext = "${food.macronutrients.kcal} kcal",
              onClick = { onLogFood(food.id) },
              onDelete = onDelete,
              onEdit = onEdit,
            )
          },
          isLoading = state.isLoading,
          onScrolledToBottom = {
            onEvent(SearchEvent.LoadNextPage)
          },
        )
      } else if (state.isLoading) {
        LoadingFiller()
      } else {
        ContentSurface(
          modifier = Modifier.padding(horizontal = spacing),
        ) {
          Icon(
            painter = painterResource(R.drawable.sentiment_stressed_24px),
            contentDescription = stringResource(R.string.content_desc_missing_data),
            modifier = Modifier.size(96.dp),
            tint = MaterialTheme.colorScheme.primary,
          )
          Spacer(Modifier.height(16.dp))
          Text(
            text = stringResource(R.string.msg_no_food_found),
            style = MaterialTheme.typography.labelLarge,
            textAlign = TextAlign.Center,
          )
        }
      }
    }
  }
}
