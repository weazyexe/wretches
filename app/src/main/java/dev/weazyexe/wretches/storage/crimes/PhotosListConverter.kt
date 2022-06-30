package dev.weazyexe.wretches.storage.crimes

import android.net.Uri
import androidx.room.TypeConverter

class PhotosListConverter {

    companion object {

        private const val SEPARATOR = ", "
    }

    @TypeConverter
    fun toString(photos: List<Uri>): String {
        return photos.joinToString(separator = SEPARATOR) { it.toString() }
    }

    @TypeConverter
    fun toPhotosList(rawString: String): List<Uri> {
        return rawString.split(SEPARATOR).map { Uri.parse(it) }
    }
}