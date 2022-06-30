package dev.weazyexe.wretches.storage.crimes

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import dev.weazyexe.wretches.entity.Crime

@Dao
interface CrimesDao {

    @Query("SELECT * FROM crime")
    suspend fun getAll(): List<Crime>

    @Insert
    suspend fun saveAll(crimes: List<Crime>)

    @Insert
    suspend fun save(crime: Crime)
}