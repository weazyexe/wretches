package dev.weazyexe.wretches.ui.settings

import dev.weazyexe.wretches.entity.Theme

data class SettingsState(
    val theme: Theme = Theme.SYSTEM
)

sealed class SettingsEffect