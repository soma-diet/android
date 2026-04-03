package dev.skaba.soma.app.ui.features.log.components.scaffold

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import dev.skaba.soma.app.R
import dev.skaba.soma.app.ui.theme.SOMATheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogScreenTopBar() {
  TopAppBar(
    title = {
      Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
      ) {
        ChangeDayButton(
          icon = R.drawable.arrow_back,
          description = "Go back a day",
          onClick = {
            // TODO change day back
          }
        )
        Text("Today")
        ChangeDayButton(
          icon = R.drawable.arrow_forward,
          description = "Go forward a day",
          onClick = {
            // TODO change day forward
          }
        )
      }
    }
  )
}

@Composable
private fun ChangeDayButton(
  icon: Int,
  description: String,
  onClick: () -> Unit
) {
  IconButton(
    onClick = onClick
  ) {
    Icon(
      painter = painterResource(icon),
      contentDescription = description
    )
  }
}

@Preview(showBackground = true)
@Composable
private fun LogScreenTopBarPreview() {
  SOMATheme {
    LogScreenTopBar()
  }
}