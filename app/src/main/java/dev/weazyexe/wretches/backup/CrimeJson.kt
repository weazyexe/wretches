package dev.weazyexe.wretches.backup

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CrimeJson(
    @SerialName("id") val id: String,
    @SerialName("title") val title: String,
    @SerialName("description") val description: String,
    @SerialName("isSolved") val isSolved: Boolean,
    @SerialName("photos") val photos: List<String> = emptyList()
)