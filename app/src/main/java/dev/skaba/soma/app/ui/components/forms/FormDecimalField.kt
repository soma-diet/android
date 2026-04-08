package dev.skaba.soma.app.ui.components.forms

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun FormDecimalField(
  name: String,
  value: MutableState<Float?>,
  placeholder: String,
  modifier: Modifier = Modifier,
  error: String? = null,
  required: Boolean = true,
  onValueChange: (Float?) -> Unit = {},
) {
  // bez by protoze android studio si pak mysli ze je nepouzivana?
  val stringValue = remember {
    mutableStateOf(
      value.value?.let {
        if (it % 1f == 0f) it.toInt()
          .toString() // pokud vyjde na cely cislo tak vypsat jako cislo bez tecky
        else it.toString()  // bohuzel
      } ?: ""
    )
  }

  FormInputField(
    name = name,
    modifier = modifier,
    error = error,
    required = required,
  ) {
    BasicTextField(
      value = stringValue.value,
      onValueChange = { input ->
        val safeInput = input.replace(',', '.') // pro ceskou klavesnici

        if (safeInput.isEmpty()) {
          stringValue.value = ""
          value.value = null
          onValueChange(null)
        } else {
          val converted = safeInput.toFloatOrNull()

          if (converted != null) {
            stringValue.value = safeInput
            value.value = converted
            onValueChange(converted)
          }
        }
      },
      keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
      modifier = modifier,
      textStyle = MaterialTheme.typography.bodyLarge.copy(
        color = if (error == null || error == "") MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.error,
        textAlign = TextAlign.End
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
          if (stringValue.value.isEmpty()) {
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