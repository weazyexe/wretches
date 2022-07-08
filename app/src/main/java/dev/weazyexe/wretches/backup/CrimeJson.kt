package dev.weazyexe.wretches.backup

import dev.weazyexe.wretches.entity.Crime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Сущность для хранения преступления [Crime] в формате JSON
 */
@Serializable
data class CrimeJson(
    @SerialName("id") val id: String,
    @SerialName("title") val title: String,
    @SerialName("description") val description: String,
    @SerialName("isSolved") val isSolved: Boolean,
    @SerialName("photos") val photos: List<String> = emptyList()
)