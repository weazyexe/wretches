package dev.weazyexe.wretches.entity

import android.net.Uri
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 * Сущность преступления
 */
@Entity
@Parcelize
data class Crime(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val isSolved: Boolean,
    val photos: List<Uri> = emptyList()
) : Parcelable