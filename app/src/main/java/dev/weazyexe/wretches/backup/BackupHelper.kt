package dev.weazyexe.wretches.backup

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Base64
import androidx.core.net.toUri
import dev.weazyexe.wretches.entity.Crime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*

class BackupHelper(context: Context) {

    private val contentResolver = context.contentResolver
    private val filesDir = context.filesDir

    suspend fun backup(uri: Uri, crimes: List<Crime>): Boolean = withContext(Dispatchers.IO) {
        return@withContext try {
            val json = crimesAsJson(crimes)
            contentResolver.openOutputStream(uri)?.use {
                it.write(json.toByteArray(Charsets.UTF_8))
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun restore(uri: Uri): List<Crime> = withContext(Dispatchers.IO) {
        return@withContext try {
            contentResolver.openInputStream(uri)?.use {
                val json = it.readBytes().toString(Charsets.UTF_8)
                return@withContext jsonAsCrimes(json)
            }
            emptyList()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    private fun crimesAsJson(crimes: List<Crime>): String {
        val jsonEntities = crimes.asJsonEntities()
        return Json.encodeToString(jsonEntities)
    }

    private fun jsonAsCrimes(json: String): List<Crime> {
        val jsonEntities = Json.decodeFromString<List<CrimeJson>>(json)
        return jsonEntities.asAppEntities()
    }

    private fun List<Crime>.asJsonEntities(): List<CrimeJson> = map {
        CrimeJson(
            id = it.id,
            title = it.title,
            description = it.description,
            isSolved = it.isSolved,
            photos = it.photos.asBase64Strings()
        )
    }

    private fun List<Uri>.asBase64Strings(): List<String> = mapNotNull { uri ->
        val bitmap = uri.toBitmap()
        val baos = bitmap.compress()
        return@mapNotNull Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT)
    }

    private fun Bitmap.compress(): ByteArrayOutputStream {
        val baos = ByteArrayOutputStream()
        compress(Bitmap.CompressFormat.JPEG, 80, baos)
        return baos
    }

    private fun Uri.toBitmap(): Bitmap {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val source = ImageDecoder.createSource(contentResolver, this)
            ImageDecoder.decodeBitmap(source)
        } else {
            MediaStore.Images.Media.getBitmap(contentResolver, this)
        }
    }

    private fun List<CrimeJson>.asAppEntities(): List<Crime> = map {
        Crime(
            id = it.id,
            title = it.title,
            description = it.description,
            isSolved = it.isSolved,
            photos = it.photos.saveImagesToInternalStorage()
        )
    }

    private fun List<String>.saveImagesToInternalStorage(): List<Uri> = map {
        val file = File(filesDir, UUID.randomUUID().toString())
        file.writeBytes(Base64.decode(it, Base64.DEFAULT))
        file.toUri()
    }
}