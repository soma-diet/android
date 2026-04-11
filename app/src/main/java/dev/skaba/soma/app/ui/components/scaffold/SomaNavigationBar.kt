package dev.skaba.soma.app.ui.components.scaffold

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.skaba.soma.app.R
import dev.skaba.soma.app.ui.navigation.LogScreenRoute
import dev.skaba.soma.app.ui.navigation.SearchScreenRoute
import dev.skaba.soma.app.ui.navigation.TargetsFormScreenRoute
import dev.skaba.soma.app.ui.theme.SOMATheme

data class BottomNavigationItem(
  val name: String, // pro content description
  val icon: Int,
  val route: Any,
)

val navigationItems = listOf(
  BottomNavigationItem(
    name = "Search screen",
    icon = R.drawable.search_24px,
    route = SearchScreenRoute,
  ),
  BottomNavigationItem(
    name = "Log screen",
    icon = R.drawable.home_24px,
    route = LogScreenRoute,
  ),
  BottomNavigationItem(
    name = "Targets form screen",
    icon = R.drawable.target_24px,
    route = TargetsFormScreenRoute,
  ),
)

@Composable
fun SomaNavigationBar(navController: NavHostController) {
  val navBackStackEntry by navController.currentBackStackEntryAsState()
  val currentDestination = navBackStackEntry?.destination

  NavigationBar(
    modifier = Modifier
      .fillMaxWidth()
      .clip(
        MaterialTheme.shapes.medium.copy(
          bottomStart = CornerSize(0.dp),
          bottomEnd = CornerSize(0.dp),
        ),
      ),
    containerColor = MaterialTheme.colorScheme.surface,
  ) {
    navigationItems.forEach { item ->
      // najde jestli v sekvenci navigaci je route na item (::class = route)
      val isSelected = currentDestination?.hierarchy?.any {
        it.hasRoute(item.route::class)
      } == true

      NavigationBarItem(
        selected = isSelected,
        icon = {
          Icon(
            painter = painterResource(item.icon),
            contentDescription = item.name,
          )
        },
        colors = NavigationBarItemDefaults.colors(
          selectedIconColor = MaterialTheme.colorScheme.primary,
          unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
          indicatorColor = MaterialTheme.colorScheme.surface,
        ),
        onClick = {
          navController.navigate(item.route) {
            // popUpTo = vycisti historii navigace az po posledni destinaci
            popUpTo(navController.graph.findStartDestination().id) {
              saveState = true // nenicit ji uplne, zapamatovat stav
            }
            launchSingleTop = true // neotevirat dvakrat stejnou obrazovku
            restoreState = true // nacist ulozeny stav (pair s saveState)
          }
        },
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
private fun SomaNavigationBarPreview() {
  SOMATheme {
    SomaNavigationBar(rememberNavController())
  }
}