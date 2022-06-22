package dev.weazyexe.wretches.entity

import android.net.Uri
import java.io.Serializable

data class Crime(
    val id: String,
    val title: String,
    val description: String,
    val isSolved: Boolean,
    val photos: List<Uri> = emptyList()
) : Serializable