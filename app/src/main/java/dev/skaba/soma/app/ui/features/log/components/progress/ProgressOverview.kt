package dev.skaba.soma.app.ui.features.log.components.progress

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.skaba.soma.app.ui.theme.SOMATheme

@Composable
fun ProgressOverview(modifier: Modifier = Modifier) {
  Surface(
    modifier = modifier.fillMaxWidth(),
    shape = MaterialTheme.shapes.medium,
  ) {
    Column(
      horizontalAlignment = Alignment.Start,
      verticalArrangement = Arrangement.spacedBy(8.dp),
      modifier = Modifier.padding(16.dp)
    ) {
      TargetProgress(
        name = "Energy",
        unit = "kcal",
        current = 356.5f,
        max = 2000.0f
      )
      TargetProgress(
        name = "Protein",
        unit = "g",
        current = 67.5f,
        max = 150.0f
      )
      TargetProgress(
        name = "Fats",
        unit = "g",
        current = 67.5f,
        max = 150.0f
      )
      TargetProgress(
        name = "Carbs",
        unit = "g",
        current = 67.5f,
        max = 150.0f
      )
    }
  }
}

@Composable
private fun TargetProgress(
  name: String,
  unit: String,
  current: Float,
  max: Float
) {
  val ratio = current / max

  Column(
    verticalArrangement = Arrangement.spacedBy(8.dp)
  ) {
    Row(
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier.fillMaxWidth()
    ) {
      Text(text = name, style = MaterialTheme.typography.bodyLarge)
      Text(text = "$current / ${max.toString().removeSuffix(".0")} $unit", style = MaterialTheme.typography.labelMedium)
    }
    LinearProgressIndicator(
      progress = {
        // omezi ratio na range (0,1)
        ratio.coerceIn(0f, 1f)
      },
      strokeCap = StrokeCap.Butt, // vypnout pillshaped design
      modifier = Modifier
        .fillMaxWidth()
        .height(8.dp)
        .clip(MaterialTheme.shapes.small),
      drawStopIndicator = {},
      gapSize = 0.dp,
      trackColor = MaterialTheme.colorScheme.surfaceVariant
    )
  }
}

@Preview(showBackground = true)
@Composable
private fun ProgressOverviewPreview() {
  SOMATheme {
    ProgressOverview()
  }
}