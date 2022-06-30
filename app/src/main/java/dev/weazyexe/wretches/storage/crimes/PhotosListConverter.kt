package dev.weazyexe.wretches.storage.crimes

import android.net.Uri
import androidx.room.TypeConverter

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