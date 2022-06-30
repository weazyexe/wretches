package dev.weazyexe.wretches.storage.crimes

import android.content.Context
import androidx.room.Room
import dev.weazyexe.wretches.entity.Crime

class CrimesStorage(context: Context) {

    private val database = Room.databaseBuilder(
        context, CrimesDatabase::class.java, "crimes"
    ).build()

    private val dao = database.crimesDao()

    suspend fun getAll(): List<Crime> = dao.getAll()

    suspend fun saveAll(crimes: List<Crime>) = dao.saveAll(crimes)

    suspend fun save(crime: Crime) = dao.save(crime)
}