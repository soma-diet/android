package dev.skaba.soma.app.ui.components.boxes

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import dev.skaba.soma.app.R

@Composable
fun ImageBox(
  imageModel: Any?, // coil prelozi zvladne interpretovat URL i Uri, takze se pouziva any
  modifier: Modifier = Modifier,
  subtext: String? = null,
  content: (@Composable BoxScope.() -> Unit)? = null, // BoxScope aby vedel ze to bude v boxu a mohl pouzit Modifier.align
) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .border(
        width = 1.dp,
        color = MaterialTheme.colorScheme.outline,
        shape = MaterialTheme.shapes.medium
      ),
    contentAlignment = Alignment.Center
  ) {
    if (imageModel != null && imageModel.toString().isNotBlank()) {
      AsyncImage(
        model = imageModel,
        contentDescription = "Food image",
        contentScale = ContentScale.Fit,
        modifier = Modifier.fillMaxSize()
      )
      if (content != null) {
        content()
      }
    } else {
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        Icon(
          painter = painterResource(id = R.drawable.outline_image_24),
          contentDescription = "No image",
          modifier = Modifier.size(48.dp),
        )

        if (subtext != null) {
          Text(text = subtext, style = MaterialTheme.typography.labelLarge)
        }
      }
    }
  }
}