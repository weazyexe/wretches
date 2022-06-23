package dev.weazyexe.wretches.app

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.color.DynamicColors
import dev.weazyexe.wretches.storage.SettingsStorage
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class App : Application() {

    val settingsStorage by lazy { SettingsStorage(applicationContext) }

    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
        MainScope().launch {
            AppCompatDelegate.setDefaultNightMode(settingsStorage.getTheme().systemUiMode)
        }
    }
}