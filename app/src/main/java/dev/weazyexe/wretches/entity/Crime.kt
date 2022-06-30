package dev.weazyexe.wretches.entity

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Crime(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val isSolved: Boolean,
    val photos: List<Uri> = emptyList()
) : Serializable