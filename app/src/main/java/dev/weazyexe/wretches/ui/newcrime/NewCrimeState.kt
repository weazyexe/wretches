package dev.weazyexe.wretches.ui.newcrime

import android.net.Uri
import androidx.annotation.StringRes
import dev.weazyexe.wretches.R

data class NewCrimeState(
    @StringRes val toolbarTitleRes: Int = R.string.new_crime_title_text,
    val id: String? = null,
    val title: String = "",
    val description: String = "",
    val isSolved: Boolean = false,
    val photos: List<Uri> = emptyList()
)

sealed class NewCrimeEffect {
    object GoBack : NewCrimeEffect()
}