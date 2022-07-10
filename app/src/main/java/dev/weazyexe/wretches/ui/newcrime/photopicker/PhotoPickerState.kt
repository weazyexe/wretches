package dev.weazyexe.wretches.ui.newcrime.photopicker

import android.net.Uri

/**
 * Состояние диалога [PhotoPickerDialog]
 */
data class PhotoPickerState(
    val photos: List<Uri> = emptyList()
)

/**
 * Сайд эффекты диалога [PhotoPickerDialog]
 */
sealed class PhotoPickerEffect