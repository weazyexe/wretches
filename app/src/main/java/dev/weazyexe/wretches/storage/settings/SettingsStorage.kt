package dev.weazyexe.wretches.storage.settings

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dev.weazyexe.wretches.entity.Theme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

/**
 * Хранилище с настройками приложения.
 * Обёртка над Jetpack DataStore
 */
class SettingsStorage(private val context: Context) {

    companion object {

        private const val DATA_STORE_SETTINGS = "DATA_STORE_SETTINGS"
        private const val KEY_THEME = "KEY_THEME"
    }

    private val Context.dataStore by preferencesDataStore(name = DATA_STORE_SETTINGS)
    private val keyTheme = stringPreferencesKey(KEY_THEME)

    suspend fun getTheme(): Theme = withContext(Dispatchers.IO) {
        val rawTheme = context.dataStore.data
            .map { prefs -> prefs[keyTheme] }
            .catch { emit(Theme.SYSTEM.name) }
            .first()
        return@withContext if (rawTheme != null) {
            Theme.valueOf(rawTheme)
        } else {
            Theme.SYSTEM
        }
    }

    suspend fun saveTheme(theme: Theme): Boolean = withContext(Dispatchers.IO) {
        return@withContext try {
            context.dataStore.edit { prefs ->
                prefs[keyTheme] = theme.name
            }
            true
        } catch (e: Exception) {
            false
        }
    }
}