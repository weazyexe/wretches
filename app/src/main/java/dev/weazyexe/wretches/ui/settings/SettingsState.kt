package dev.weazyexe.wretches.ui.settings

import androidx.annotation.StringRes
import dev.weazyexe.wretches.entity.Theme

data class SettingsState(
    val theme: Theme = Theme.SYSTEM,
    val hasCrimes: Boolean = false
)

sealed class SettingsEffect {

    data class ShowSnackbar(@StringRes val messageRes: Int) : SettingsEffect()
}