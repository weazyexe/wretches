package dev.weazyexe.wretches.entity

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Сущность преступления
 * TODO сделать сущность Entity
 */
@Parcelize
data class Crime(
    val id: String,
    val title: String,
    val description: String,
    val isSolved: Boolean,
    val photos: List<Uri> = emptyList()
) : Parcelable