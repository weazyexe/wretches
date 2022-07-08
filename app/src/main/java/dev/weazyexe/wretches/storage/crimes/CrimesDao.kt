package dev.weazyexe.wretches.storage.crimes

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import dev.weazyexe.wretches.entity.Crime

/**
 * Интерфейс для работы с БД с преступлениями
 */
@Dao
interface CrimesDao {

    @Query("SELECT * FROM crime")
    suspend fun getAll(): List<Crime>

    @Insert(onConflict = REPLACE)
    suspend fun saveAll(crimes: List<Crime>)

    @Insert(onConflict = REPLACE)
    suspend fun save(crime: Crime)

    @Query("DELETE FROM crime")
    suspend fun deleteAll()
}