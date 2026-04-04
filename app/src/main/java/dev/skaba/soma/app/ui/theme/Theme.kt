package dev.skaba.soma.app.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

// surfaceVariant = sekundarni povrch na surface barve

private val DarkColorScheme = darkColorScheme(
  background = ColorBlack,
  surface = ColorSemiBlack,
  surfaceVariant = ColorGray,

  primary = ColorRed,
  onPrimary = ColorWhite,

  secondary = ColorSemiWhite,
  onSecondary = ColorBlack,

  onBackground = ColorWhite,
  onSurface = ColorWhite,
  onSurfaceVariant = ColorSemiWhite,

  error = ColorRed,
  onError = ColorWhite,
  tertiary = ColorGreen,
  onTertiary = ColorBlack
)

private val LightColorScheme = lightColorScheme(
  background = ColorWhite,
  surface = ColorSemiGray,
  surfaceVariant = ColorSubtleGray,

  primary = ColorBlack,
  onPrimary = ColorWhite,

  secondary = ColorSemiWhite,
  onSecondary = ColorBlack,

  onBackground = ColorBlack,
  onSurface = ColorBlack,
  onSurfaceVariant = ColorSemiWhite,

  error = ColorRed,
  onError = ColorWhite,
  tertiary = ColorGreen,
  onTertiary = ColorWhite
)

@Composable
fun SOMATheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = true,
  content: @Composable () -> Unit
) {
  val colorScheme = when {
    dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
      val context = LocalContext.current
      if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
    }

    darkTheme -> DarkColorScheme
    else -> LightColorScheme
  }

  // DEV pro testovani overriduju
  val overridenScheme = DarkColorScheme

  MaterialTheme(
    colorScheme = overridenScheme,
    typography = AppTypography,
    content = content,
    shapes = AppShapes
  )
}