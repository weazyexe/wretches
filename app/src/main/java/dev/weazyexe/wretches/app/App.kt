package dev.weazyexe.wretches.app

import android.app.Application
import com.google.android.material.color.DynamicColors

/**
 * Базовый [Application] класс приложения
 */
class App : Application() {

    // Хранилища
    // TODO создать PhotoStorage
    // TODO создать SettingsStorage
    // TODO создать CrimesStorage

    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)    // Поддержка Material You
        // TODO менять тему при старте приложения
    }
}