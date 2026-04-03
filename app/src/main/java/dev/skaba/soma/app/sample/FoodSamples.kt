package dev.skaba.soma.app.sample

import dev.skaba.soma.app.domain.model.Food
import dev.skaba.soma.app.domain.model.Macronutrients
import dev.skaba.soma.app.domain.model.Micronutrients
import dev.skaba.soma.app.domain.model.Serving

object FoodPreviewData {
  val sampleFood1 = Food(
    id = "1",
    name = "Kuřecí prsa (syrová)",
    isMass = true,
    isPrivate = false,
    brand = "Vodňanské kuře",
    macronutrients = Macronutrients(
      kcal = 110f,
      protein = 23f,
      fats = 1.2f,
      carbs = 0f
    ),
    servings = listOf(
      Serving(id = "s1", name = "100g", size = 1f),
      Serving(id = "s2", name = "Balení", size = 5f)
    )
  )

  val sampleFood2 = Food(
    id = "2",
    name = "Skyr Natur",
    isMass = true,
    isPrivate = true,
    author = "Robin",
    brand = "Milko",
    macronutrients = Macronutrients(
      kcal = 62f,
      protein = 11f,
      fats = 0.1f,
      carbs = 4f
    ),
    micronutrients = Micronutrients(
      fiber = 0f,
      sodium = 0.1f
    ),
    servings = listOf(
      Serving(id = "s3", name = "Kelímek", size = 1.4f)
    )
  )

  val sampleFood3 = Food(
    id = "3",
    name = "Horalka",
    isMass = false, // Kusovka
    isPrivate = false,
    barcode = "8586000130017",
    macronutrients = Macronutrients(
      kcal = 540f,
      protein = 8f,
      fats = 33f,
      carbs = 51f
    )
  )

  val allSamples = listOf(sampleFood1, sampleFood2, sampleFood3)
}