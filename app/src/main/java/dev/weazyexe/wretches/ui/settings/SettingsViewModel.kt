package dev.weazyexe.wretches.ui.settings

import android.app.Application
import android.net.Uri
import androidx.lifecycle.viewModelScope
import dev.weazyexe.wretches.backup.BackupHelper
import dev.weazyexe.wretches.entity.Theme
import dev.weazyexe.wretches.ui.common.BaseViewModel
import kotlinx.coroutines.launch

/**
 * Вью модель экрана [SettingsViewModel]
 */
class SettingsViewModel(
    application: Application
) : BaseViewModel<SettingsState, SettingsEffect>(application) {

    override val initialState: SettingsState = SettingsState()
    private val backupHelper = BackupHelper(application.applicationContext)

    init {
        updateTheme()
        updateCrimesPresence()
    }

    fun getCurrentTheme(): Theme = state.value.theme

    fun hasCrimes(): Boolean = state.value.hasCrimes

    fun setTheme(theme: Theme) = viewModelScope.launch {
        // TODO устанавливать тему и сохранять ее в префы
    }

    fun backup(uri: Uri) = viewModelScope.launch {
        // TODO забэкапить
    }

    fun restore(uri: Uri) = viewModelScope.launch {
        // TODO восстановить
    }

    private fun updateTheme() = viewModelScope.launch {
        // TODO считывать тему из префов для обновления на UI
    }

    private fun updateCrimesPresence() = viewModelScope.launch {
        // TODO считать преступления из хранилища
    }
}