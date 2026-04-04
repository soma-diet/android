package dev.skaba.soma.app.ui.components.forms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FormSection(
  title: String,
  modifier: Modifier = Modifier,
  content: @Composable () -> Unit,
) {
  Surface(
    shape = MaterialTheme.shapes.medium, modifier = modifier.fillMaxWidth()
  ) {
    Column(
      verticalArrangement = Arrangement.spacedBy(8.dp),
      modifier = Modifier.padding(16.dp),
    ) {
      Text(text = title, style = MaterialTheme.typography.headlineMedium)
      HorizontalDivider()
      content()
    }
  }
}