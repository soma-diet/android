package dev.skaba.soma.app.ui.components.scaffold

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.skaba.soma.app.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SomaAppBar(
  title: @Composable () -> Unit,
  modifier: Modifier = Modifier,
  navigationIcon: @Composable () -> Unit = {},
  actions: @Composable RowScope.() -> Unit = {},
) {
  CenterAlignedTopAppBar(
    title = title,
    navigationIcon = navigationIcon,
    actions = actions,
    modifier = modifier.clip(
      MaterialTheme.shapes.medium.copy(
        topStart = CornerSize(0.dp),
        topEnd = CornerSize(0.dp),
      ),
    ),
    windowInsets = WindowInsets(0.dp),
  )
}

@Composable
fun SomaTextOnlyAppBar(
  text: String,
  modifier: Modifier = Modifier,
  onNavigateBack: (() -> Unit)? = null,
) {
  SomaAppBar(
    title = {
      Text(
        text = text,
        style = MaterialTheme.typography.headlineMedium,
      )
    },
    modifier = modifier,
    navigationIcon = {
      if (onNavigateBack != null) {
        IconButton(onClick = onNavigateBack) {
          Icon(
            painter = painterResource(id = R.drawable.arrow_back),
            contentDescription = stringResource(R.string.content_desc_go_back),
          )
        }
      }
    },
  )
}
