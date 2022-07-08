package dev.weazyexe.wretches.app

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.color.DynamicColors
import dev.weazyexe.wretches.storage.crimes.CrimesStorage
import dev.weazyexe.wretches.storage.photo.PhotoStorage
import dev.weazyexe.wretches.storage.settings.SettingsStorage
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class App : Application() {

    val settingsStorage by lazy { SettingsStorage(applicationContext) }
    val crimesStorage by lazy { CrimesStorage(applicationContext) }
    val photoStorage by lazy { PhotoStorage(applicationContext) }

    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
        MainScope().launch {
            AppCompatDelegate.setDefaultNightMode(settingsStorage.getTheme().systemUiMode)
        }
    }
}