package dev.weazyexe.wretches.storage.crimes

import android.content.Context
import androidx.room.Room
import dev.weazyexe.wretches.entity.Crime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CrimesStorage(context: Context) {

    private val database = Room.databaseBuilder(
        context, CrimesDatabase::class.java, "crimes"
    ).build()

    private val dao = database.crimesDao()

    suspend fun getAll(): List<Crime> = withContext(Dispatchers.IO) {
        dao.getAll()
    }

    suspend fun rewriteAll(crimes: List<Crime>) = withContext(Dispatchers.IO) {
        dao.deleteAll()
        dao.saveAll(crimes)
    }

    suspend fun save(crime: Crime) = withContext(Dispatchers.IO) {
        dao.save(crime)
    }
}