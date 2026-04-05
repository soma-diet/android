package dev.skaba.soma.app.ui.features.search.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SearchField(
  filters: List<String>,
  selectedFilter: String,
  onQueryChanged: (String) -> Unit,
  onFilterChanged: (String) -> Unit,
  modifier: Modifier = Modifier,
) {
  var query by remember { mutableStateOf("") }
  var selectedFilter by remember { mutableStateOf(selectedFilter) }
  Box(
    modifier = modifier
  ) {
    Column {
      val elementShape = MaterialTheme.shapes.small
      OutlinedTextField(
        value = query,
        onValueChange = { newQuery ->
          query = newQuery
          onQueryChanged(newQuery)
        },
        placeholder = { Text(text = "Search foods..") },
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
          unfocusedContainerColor = MaterialTheme.colorScheme.surface,
          focusedContainerColor = MaterialTheme.colorScheme.surface
        ),
        shape = elementShape.copy(
          bottomStart = CornerSize(0.dp),
          bottomEnd = CornerSize(0.dp)
        )
      )
      Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
          .fillMaxWidth()
          .height(40.dp)
      ) {
        filters.forEachIndexed { index, filter ->
          val btmEndCorner =
            if (index == filters.lastIndex) elementShape.bottomEnd else CornerSize(0.dp)
          val btmStartCorner = if (index == 0) elementShape.bottomStart else CornerSize(0.dp)
          Button(
            onClick = {
              selectedFilter = filter
              onFilterChanged(filters[index])
            },
            modifier = Modifier
              .weight(1f)
              .fillMaxHeight(),
            colors = ButtonDefaults.buttonColors(
              containerColor = if (selectedFilter == filter) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
              contentColor = if (selectedFilter == filter) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
            ),
            shape = elementShape.copy(
              topStart = CornerSize(0.dp),
              topEnd = CornerSize(0.dp),
              bottomStart = btmStartCorner,
              bottomEnd = btmEndCorner
            )
          ) {
            Text(text = filter)
          }
        }
      }
    }

  }

}