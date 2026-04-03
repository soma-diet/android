package dev.skaba.soma.app.data.util

import dev.skaba.soma.app.BuildConfig
import dev.skaba.soma.app.domain.model.FoodImages

private const val IMAGES_ENDPOINT = BuildConfig.BACKEND_URL + "/api/images"

fun getFoodImages(imageFilename: String): FoodImages {
  return FoodImages(
    smallUrl = "${IMAGES_ENDPOINT}/small_${imageFilename}",
    largeUrl = "${IMAGES_ENDPOINT}/large_${imageFilename}"
  )
}
