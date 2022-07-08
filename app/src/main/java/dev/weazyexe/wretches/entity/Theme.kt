package dev.weazyexe.wretches.entity

import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatDelegate
import dev.weazyexe.wretches.R

/**
 * Цветовая тема приложения
 */
enum class Theme {

    /**
     * Светлая тема
     */
    LIGHT,

    /**
     * Тёмная тема
     */
    DARK,

    /**
     * Как в системе
     */
    SYSTEM;

    /**
     * Получение UI-friendly строки для названия темы
     */
    @get:StringRes
    val stringRes: Int
        get() = when (this) {
            LIGHT -> R.string.settings_theme_value_light_text
            DARK -> R.string.settings_theme_value_dark_text
            SYSTEM -> R.string.settings_theme_value_system_text
        }

    /**
     * Конвертация нашей темы в андроид-сущность UI mode
     */
    val systemUiMode: Int
        get() = when(this) {
            LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
            DARK -> AppCompatDelegate.MODE_NIGHT_YES
            SYSTEM -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }
}