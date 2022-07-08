package dev.weazyexe.wretches.storage.crimes

import android.net.Uri
import androidx.room.TypeConverter

/**
 * Конвертер для списка [Uri]
 * Объект [Uri] не может сохраниться в БД, поэтому его следует
 * конвертировать в объект String
 */
class PhotosListConverter {

    companion object {

        private const val SEPARATOR = ", "
    }

    @TypeConverter
    fun toString(photos: List<Uri>): String {
        return if (photos.isNotEmpty()) {
            photos.joinToString(separator = SEPARATOR) { it.toString() }
        } else {
            ""
        }
    }

    @TypeConverter
    fun toPhotosList(rawString: String): List<Uri> {
        return if (rawString != "") {
            rawString.split(SEPARATOR).map { Uri.parse(it) }
        } else {
            emptyList()
        }
    }
}