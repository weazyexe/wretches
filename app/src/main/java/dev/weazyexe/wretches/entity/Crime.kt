package dev.weazyexe.wretches.entity

import android.net.Uri

data class Crime(
    val id: String,
    val title: String,
    val description: String,
    val photoUri: Uri?,
    val isSolved: Boolean
)