package dev.skaba.soma.app.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.skaba.soma.app.di.AppContainer
import dev.skaba.soma.app.ui.components.scaffold.SomaNavigationBar
import dev.skaba.soma.app.ui.features.food.FoodFormScreen
import dev.skaba.soma.app.ui.features.food.viewmodel.FoodFormViewModel
import dev.skaba.soma.app.ui.features.food.viewmodel.FoodFormViewModelFactory
import dev.skaba.soma.app.ui.features.log.LogScreen
import dev.skaba.soma.app.ui.features.log.viewmodel.LogViewModel
import dev.skaba.soma.app.ui.features.log.viewmodel.LogViewModelFactory
import dev.skaba.soma.app.ui.features.log_entry.LogEntryScreen
import dev.skaba.soma.app.ui.features.search.SearchScreen
import dev.skaba.soma.app.ui.features.search.viewmodel.SearchViewModel
import dev.skaba.soma.app.ui.features.search.viewmodel.SearchViewModelFactory
import dev.skaba.soma.app.ui.features.targets.TargetsScreen
import dev.skaba.soma.app.ui.features.targets.viewmodel.TargetsViewModel
import dev.skaba.soma.app.ui.features.targets.viewmodel.TargetsViewModelFactory
import dev.skaba.soma.app.ui.navigation.FoodFormScreenRoute
import dev.skaba.soma.app.ui.navigation.LogEntryScreenRoute
import dev.skaba.soma.app.ui.navigation.LogScreenRoute
import dev.skaba.soma.app.ui.navigation.SearchScreenRoute
import dev.skaba.soma.app.ui.navigation.TargetsFormScreenRoute

@Composable
fun SomaApp(appContainer: AppContainer) {
  val navController = rememberNavController()

  Scaffold(
    bottomBar = { SomaNavigationBar(navController = navController) },
  ) { globalPadding ->
    NavHost(
      navController = navController,
      startDestination = LogScreenRoute,
      modifier = Modifier.padding(globalPadding),
    ) {
      composable<FoodFormScreenRoute> {
        val foodFormViewModel: FoodFormViewModel = viewModel(
          factory = FoodFormViewModelFactory(
            foodRepository = appContainer.foodRepository,
            imageProcessor = appContainer.imageProcessor,
          ),
        )
        FoodFormScreen(
          viewModel = foodFormViewModel,
          onFoodSaved = {
            navController.navigate(SearchScreenRoute)
          },
          navigateBack = { navController.popBackStack() },
        )
      }

      composable<LogScreenRoute> {
        val logViewModel: LogViewModel = viewModel(
          factory = LogViewModelFactory(
            logEntryRepository = appContainer.logEntryRepository,
            targetsRepository = appContainer.targetsRepository,
          ),
        )
        LogScreen(
          viewModel = logViewModel,
          onEditEntry = { /* TODO: edit navigation */ }
        )
      }

      composable<SearchScreenRoute> {
        val searchViewModel: SearchViewModel = viewModel(
          factory = SearchViewModelFactory(
            foodRepository = appContainer.foodRepository,
          ),
        )
        SearchScreen(
          searchViewModel = searchViewModel,
          navigateToNewFoodScreen = { navController.navigate(FoodFormScreenRoute(null)) },
          navigateToEditScreen = { foodId -> navController.navigate(FoodFormScreenRoute(foodId)) },
        )
      }

      composable<TargetsFormScreenRoute> {
        val targetsViewModel: TargetsViewModel = viewModel(
          factory = TargetsViewModelFactory(
            targetsRepository = appContainer.targetsRepository,
          ),
        )
        TargetsScreen(
          viewModel = targetsViewModel,
          navigateBack = { navController.popBackStack() },
          navigateToLogScreen = { navController.navigate(LogScreenRoute) },
        )
      }
      composable<LogEntryScreenRoute> { LogEntryScreen() }
    }
  }
}
