package dev.skaba.soma.app.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import dev.skaba.soma.app.R

val OutfitFontFamily = FontFamily(
  Font(R.font.outfit_light, FontWeight.Light),
  Font(R.font.outfit_regular, FontWeight.Normal),
  Font(R.font.outfit_semibold, FontWeight.SemiBold),
  Font(R.font.outfit_bold, FontWeight.Bold)
)

val AppTypography = Typography(
  headlineLarge = TextStyle(
    fontFamily = OutfitFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 32.sp
  ),
  headlineMedium = TextStyle(
    fontFamily = OutfitFontFamily,
    fontWeight = FontWeight.SemiBold,
    fontSize = 28.sp,
  ),
  headlineSmall = TextStyle(
    fontFamily = OutfitFontFamily,
    fontWeight = FontWeight.SemiBold,
    fontSize = 24.sp,
  ),

  bodyLarge = TextStyle(
    fontFamily = OutfitFontFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 20.sp,
  ),
  bodyMedium = TextStyle(
    fontFamily = OutfitFontFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp,
  ),
  bodySmall = TextStyle(
    fontFamily = OutfitFontFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 12.sp,
  ),

  labelLarge = TextStyle(
    fontFamily = OutfitFontFamily,
    fontWeight = FontWeight.Light,
    fontSize = 20.sp,
  ),
  labelMedium = TextStyle(
    fontFamily = OutfitFontFamily,
    fontWeight = FontWeight.Light,
    fontSize = 16.sp,
  ),
  labelSmall = TextStyle(
    fontFamily = OutfitFontFamily,
    fontWeight = FontWeight.ExtraLight,
    fontSize = 12.sp,
  )
)