package dev.skaba.soma.app

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import dev.skaba.soma.app.ui.SomaApp
import dev.skaba.soma.app.ui.theme.SOMATheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    // barvy na transparent aby to matchnulo pozadi appky
    enableEdgeToEdge(
      statusBarStyle = SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT),
      navigationBarStyle = SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT),
    )

    val appContainer = (application as SomaApplication).container

    val authRepo = appContainer.authRepository
    lifecycleScope.launch {
      authRepo.signInAnonymously()
    }

    setContent {
      SOMATheme {
        SomaApp(
          appContainer = appContainer,
        )
      }
    }
  }
}
