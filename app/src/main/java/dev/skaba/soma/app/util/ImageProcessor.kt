package dev.skaba.soma.app.util

import android.content.Context
import android.provider.OpenableColumns
import androidx.core.net.toUri
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

// classa, ktera validuje velikost obrazku
class ImageProcessor(private val context: Context) {
  fun isImageSizeValid(uriString: String, maxMb: Int = 1): Boolean {
    val uri = uriString.toUri()
    var sizeBytes = 0L

    context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
      val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
      if (cursor.moveToFirst() && sizeIndex != -1) {
        sizeBytes = cursor.getLong(sizeIndex)
      }
    }

    val sizeMb = sizeBytes / (1024 * 1024)
    return sizeMb <= maxMb
  }

  fun saveImgToStorage(uriString: String, fileName: String? = null): String? {
    val uri = uriString.toUri()
    val imagesDir = File(context.filesDir, "food_images")

    if (uri.scheme == "file" && uri.path?.contains("food_images") == true) {
      return uriString
    }

    return try {
      val inputStream = context.contentResolver.openInputStream(uri) ?: return null
      val finalFileName = fileName ?: UUID.randomUUID().toString()
      val outputFile = File(imagesDir, "$finalFileName.jpg")

      imagesDir.mkdirs()

      FileOutputStream(outputFile).use { outputStream ->
        inputStream.copyTo(outputStream)
      }

      outputFile.toUri().toString()
    } catch (e: Exception) {
      e.printStackTrace()
      null
    }
  }
}