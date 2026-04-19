package dev.skaba.soma.app.ui.features.entry.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.skaba.soma.app.R
import dev.skaba.soma.app.domain.food.Food
import dev.skaba.soma.app.ui.components.boxes.ContentSurface

@SuppressLint("DefaultLocale")
@Composable
fun NutritionalInformation(
  food: Food,
  servingSize: Float,
  quantity: Float?,
  modifier: Modifier = Modifier,
) {
  val totalServingSize = if (quantity != null) {
    servingSize * quantity
  } else {
    100f
  }

  val information: Map<String, Float?> = mapOf(
    stringResource(R.string.nutrient_kcal) to food.macronutrients.kcal,
    stringResource(R.string.nutrient_fats) to food.macronutrients.fats,
    stringResource(R.string.nutrient_carbs) to food.macronutrients.carbs,
    stringResource(R.string.nutrient_protein) to food.macronutrients.protein,
    stringResource(R.string.nutrient_fiber) to food.micronutrients?.fiber,
    stringResource(R.string.nutrient_sodium) to food.micronutrients?.sodium,
  )

  ContentSurface(
    modifier = modifier.padding(16.dp),
    horizontalAlignment = Alignment.Start,
  ) {
    Text(
      text = "Nutritional information (per ${totalServingSize.toInt()}g)",
      style = MaterialTheme.typography.headlineSmall,
      textAlign = TextAlign.Left,
      modifier = Modifier.padding(bottom = 8.dp),
    )

    HorizontalDivider(modifier = Modifier.padding(bottom = 8.dp))

    for ((label, value) in information) {
      val calcVal = value?.times((totalServingSize / 100f))

      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
      ) {
        Text(text = label)
        Text(
          text = if (calcVal != null) String.format("%.1f", calcVal) else "?",
        )
      }
    }
  }
}
