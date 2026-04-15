package dev.skaba.soma.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import dev.skaba.soma.app.auth.AuthRepository
import dev.skaba.soma.app.ui.SomaApp
import dev.skaba.soma.app.ui.theme.SOMATheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    val appContainer = (application as SomaApplication).container

    val authRepo = AuthRepository()
    lifecycleScope.launch {
      val success = authRepo.signInAnonymously()
      if (success) {
        println("Anonymous user signed in, UID: ${authRepo.currentUser?.uid}")
      } else {
        println("Sign in error.")
      }
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
