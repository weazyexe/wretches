package dev.weazyexe.wretches.entity

import androidx.annotation.StringRes
import dev.weazyexe.wretches.R

enum class Theme {
    LIGHT, DARK, SYSTEM;

    @get:StringRes
    val stringRes: Int
        get() = when (this) {
            LIGHT -> R.string.settings_theme_value_light_text
            DARK -> R.string.settings_theme_value_dark_text
            SYSTEM -> R.string.settings_theme_value_system_text
        }
}