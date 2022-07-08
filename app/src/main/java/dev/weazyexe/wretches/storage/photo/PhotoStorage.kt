package dev.weazyexe.wretches.storage.photo

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Хранилище с фотографиями.
 * Обёртка над Media Store
 */
class PhotoStorage(private val context: Context) {

    suspend fun getPhotos(amount: Int = 10): List<Uri> = withContext(Dispatchers.IO) {
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DATE_ADDED
        )
        val query = context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            "${MediaStore.Images.Media.DATE_ADDED} DESC"
        ) ?: return@withContext emptyList()

        val images = mutableListOf<Uri>()
        query.use {
            var readAmount = 0
            val idColumn = query.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            while (query.moveToNext() && readAmount++ < amount) {
                val id = query.getLong(idColumn)
                images.add(
                    ContentUris.withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        id
                    )
                )
            }
        }

        return@withContext images
    }
}