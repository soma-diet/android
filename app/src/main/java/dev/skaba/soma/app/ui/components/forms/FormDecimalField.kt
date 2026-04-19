package dev.skaba.soma.app.ui.components.forms

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun FormDecimalField(
  name: String,
  value: Float?,
  placeholder: String,
  modifier: Modifier = Modifier,
  error: String? = null,
  required: Boolean = true,
  onValueChange: (Float?) -> Unit = {},
) {
  // pomocna funkce na hezke konvertovani floatu (cele cislo bez tecky)
  fun Float?.toFormattedString(): String {
    if (this == null) return ""
    return if (this % 1f == 0f) this.toInt().toString() else this.toString()
  }

  var stringValue by remember { mutableStateOf(value.toFormattedString()) }

  // updatovat stringValue podle prichozi hodnoty!
  LaunchedEffect(value) {
    val current = stringValue.replace(',', '.').toFloatOrNull()
    if (current != value) {
      stringValue = value.toFormattedString()
    }
  }

  FormInputField(
    name = name,
    modifier = modifier,
    error = error,
    required = required,
  ) {
    BasicTextField(
      value = stringValue,
      onValueChange = { input ->
        var safeInput = input.replace(',', '.') // pro ceskou klavesnici

        if (safeInput.isEmpty()) {
          stringValue = ""
          onValueChange(null)
        } else {
          // parts size == 2: cislo melo tecku, parts[1] - cast za teckou
          val parts = safeInput.split('.')
          safeInput = if (parts.size == 2 && parts[1].length > 1) {
            parts[0] + "." + parts[1][0]
          } else safeInput

          val converted = safeInput.toFloatOrNull()
          if (converted != null) {
            stringValue = safeInput
            onValueChange(converted)
          }
        }
      },
      keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
      modifier = modifier,
      textStyle = MaterialTheme.typography.bodyLarge.copy(
        color = if (error == null || error == "") MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.error,
        textAlign = TextAlign.End,
      ),
      singleLine = true,
      cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
      decorationBox = { innerTextField ->
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .padding(end = 0.dp),
          contentAlignment = Alignment.CenterEnd,
        ) {
          if (stringValue.isEmpty()) {
            Text(
              text = placeholder,
              style = MaterialTheme.typography.bodyLarge,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
          }
          innerTextField()
        }
      },
    )
  }
}