package dev.skaba.soma.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dev.skaba.soma.app.ui.features.food.FoodFormScreen
import dev.skaba.soma.app.ui.theme.SOMATheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      SOMATheme {
        FoodFormScreen()
      }
    }
  }
}
