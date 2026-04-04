package dev.skaba.soma.app.ui.components.forms

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
fun FormImageUpload(
  imageModel: Any?, // coil prelozi zvladne interpretovat URL i Uri, takze se pouziva any
  modifier: Modifier = Modifier,

  onClearImage: (() -> Unit)? = null,
) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .border(
        width = 1.dp,
        color = MaterialTheme.colorScheme.outline,
        shape = MaterialTheme.shapes.medium
      )
      .clickable {
        // TODO load image
      },
    contentAlignment = Alignment.Center,
  ) {
    if (imageModel != null && imageModel.toString().isNotBlank()) {
      AsyncImage(
        model = imageModel,
        contentDescription = "Food image",
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
      )

      if (onClearImage != null) {
        IconButton(
          onClick = onClearImage,
          modifier = Modifier
            .align(Alignment.TopEnd)
            .padding(8.dp)
            .background(
              color = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f),
              shape = RoundedCornerShape(50)
            )
        ) {
          Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Odebrat obrázek",
            tint = MaterialTheme.colorScheme.onSurface
          )
        }
      }
    } else {
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        Icon(
          painter = painterResource(id = R.drawable.image_search),
          contentDescription = "No image",
          modifier = Modifier.size(48.dp),
        )
        Text(text = "Upload an image", style = MaterialTheme.typography.labelLarge)
      }
    }
  }
}