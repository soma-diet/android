package dev.skaba.soma.app.util

import android.content.Context
import android.provider.OpenableColumns
import androidx.core.net.toUri

// classa, ktera validuje velikost obrazku (vygenerovano gemini)
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
}