package dev.skaba.soma.app.ui.components.forms

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.style.TextAlign

@Composable
fun FormTextField(
  name: String,
  value: MutableState<String>,
  placeholder: String,
  modifier: Modifier = Modifier,
  error: String? = null,
  required: Boolean = true,
  onValueChange: (String) -> Unit,
) {
  FormInputField(
    name = name,
    modifier = modifier,
    error = error,
    required = required,
  ) {
    BasicTextField(
      value = value.value,
      onValueChange = { newValue ->
        value.value = newValue
        onValueChange(newValue)
      }, modifier = modifier, textStyle = MaterialTheme.typography.bodyLarge.copy(
        color = if (error == null || error == "") MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.error,
        textAlign = TextAlign.End
      ),
      // primary color indikator psani
      cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
      singleLine = true,
      // nevykreslovat klasicky design
      decorationBox = { innerTextField ->
        Box(
          modifier = Modifier
            .fillMaxWidth(),
          contentAlignment = Alignment.CenterEnd,
        ) {
          if (value.value.isEmpty()) {
            Text(
              text = placeholder,
              style = MaterialTheme.typography.bodyLarge,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }

          innerTextField()
        }
      }
    )
  }
}