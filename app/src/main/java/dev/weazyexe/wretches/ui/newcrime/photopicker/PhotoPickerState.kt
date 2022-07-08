package dev.weazyexe.wretches.ui.newcrime.photopicker

import android.net.Uri

data class PhotoPickerState(
    val photos: List<Uri> = emptyList()
)

sealed class PhotoPickerEvent