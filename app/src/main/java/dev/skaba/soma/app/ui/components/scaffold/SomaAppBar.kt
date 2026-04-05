package dev.skaba.soma.app.ui.components.scaffold

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SomaAppBar(
  modifier: Modifier = Modifier,
  horizontalArrangement: Arrangement.Horizontal = Arrangement.Center,
  content: @Composable () -> Unit,
) {
  TopAppBar(
    title = {
      Row(
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(),
      ) {
        content()
      }
    },
    modifier = modifier
      .clip(
        // neoriznout horni rohy
        MaterialTheme.shapes.medium.copy(
          topStart = CornerSize(0.dp),
          topEnd = CornerSize(0.dp)
        )
      )
  )
}

@Composable
fun SomaTextOnlyAppBar(
  text: String,
  modifier: Modifier = Modifier,
) {
  SomaAppBar() {
    Text(
      text = text,
      style = MaterialTheme.typography.headlineMedium,
      textAlign = TextAlign.Center,
      modifier = modifier.fillMaxWidth()
    )
  }
}