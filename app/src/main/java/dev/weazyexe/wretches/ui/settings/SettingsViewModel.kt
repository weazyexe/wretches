package dev.weazyexe.wretches.ui.settings

import android.app.Application
import android.net.Uri
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.viewModelScope
import dev.weazyexe.wretches.R
import dev.weazyexe.wretches.app.App
import dev.weazyexe.wretches.backup.BackupHelper
import dev.weazyexe.wretches.entity.Theme
import dev.weazyexe.wretches.ui.common.BaseViewModel
import kotlinx.coroutines.launch

class SettingsViewModel(
    application: Application
) : BaseViewModel<SettingsState, SettingsEffect>(application) {

    override val initialState: SettingsState = SettingsState()
    private val settingsStorage = (application as App).settingsStorage
    private val crimesStorage = (application as App).crimesStorage
    private val backupHelper = BackupHelper(application.applicationContext)

    init {
        updateTheme()
        updateCrimesPresence()
    }

    fun getCurrentTheme(): Theme = state.value.theme

    fun hasCrimes(): Boolean = state.value.hasCrimes

    fun setTheme(theme: Theme) = viewModelScope.launch {
        val isThemeSaved = settingsStorage.saveTheme(theme)
        if (isThemeSaved) {
            AppCompatDelegate.setDefaultNightMode(theme.systemUiMode)
            setState { copy(theme = theme) }
        }
    }

    fun backup(uri: Uri) = viewModelScope.launch {
        val crimes = crimesStorage.getAll()
        val isSavedSuccessfully = backupHelper.backup(uri, crimes)
        SettingsEffect.ShowSnackbar(
            if (isSavedSuccessfully) {
                R.string.settings_backup_successful_text
            } else {
                R.string.settings_backup_error_text
            }
        ).emit()
    }

    fun restore(uri: Uri) = viewModelScope.launch {
        try {
            val restoredCrimes = backupHelper.restore(uri).also {
                if (it.isEmpty()) throw Exception()
            }
            crimesStorage.rewriteAll(restoredCrimes)
            SettingsEffect.ShowSnackbar(R.string.settings_restore_successful_text).emit()
        } catch (e: Exception) {
            SettingsEffect.ShowSnackbar(R.string.settings_restore_error_text).emit()
        }
    }

    private fun updateTheme() = viewModelScope.launch {
        val theme = settingsStorage.getTheme()
        setState { copy(theme = theme) }
    }

    private fun updateCrimesPresence() = viewModelScope.launch {
        val hasCrimes = crimesStorage.getAll().isNotEmpty()
        setState { copy(hasCrimes = hasCrimes) }
    }
}