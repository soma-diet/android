package dev.skaba.soma.app.ui.components.forms

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import dev.skaba.soma.app.ui.components.boxes.ImageBox

@Composable
fun FormImageUpload(
  imageModel: Any?, // coil prelozi zvladne interpretovat URL i Uri, takze se pouziva any
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  error: String? = null,

  onClearImage: (() -> Unit)? = null,
) {
  Column {
    ImageBox(
      imageModel = imageModel,
      subtext = "Upload an image",
      modifier = modifier.clickable {
        onClick()
      }
    ) {
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
    }
    if (error != null) {
      Text(
        text = error,
        color = MaterialTheme.colorScheme.error,
        style = MaterialTheme.typography.bodySmall,
        modifier = Modifier.padding(bottom = 8.dp)
      )
    }
  }
}