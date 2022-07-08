package dev.weazyexe.wretches.storage.crimes

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.weazyexe.wretches.entity.Crime

/**
 * База данных с преступлениями
 */
@Database(entities = [Crime::class], version = 1)
@TypeConverters(PhotosListConverter::class)
abstract class CrimesDatabase : RoomDatabase() {

    abstract fun crimesDao(): CrimesDao
}