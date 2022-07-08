package dev.weazyexe.wretches.app

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.color.DynamicColors
import dev.weazyexe.wretches.storage.crimes.CrimesStorage
import dev.weazyexe.wretches.storage.photo.PhotoStorage
import dev.weazyexe.wretches.storage.settings.SettingsStorage
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

/**
 * Базовый [Application] класс приложения
 */
class App : Application() {

    // Хранилища
    val settingsStorage by lazy { SettingsStorage(applicationContext) }
    val crimesStorage by lazy { CrimesStorage(applicationContext) }
    val photoStorage by lazy { PhotoStorage(applicationContext) }

    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)    // Поддержка Material You
        MainScope().launch {
            // Установка темы в соответствии с той, которая лежит в хранилище
            AppCompatDelegate.setDefaultNightMode(settingsStorage.getTheme().systemUiMode)
        }
    }
}