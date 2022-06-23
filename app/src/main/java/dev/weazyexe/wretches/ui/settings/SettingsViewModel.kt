package dev.weazyexe.wretches.ui.settings

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.viewModelScope
import dev.weazyexe.wretches.app.App
import dev.weazyexe.wretches.entity.Theme
import dev.weazyexe.wretches.ui.common.BaseViewModel
import kotlinx.coroutines.launch

class SettingsViewModel(
    application: Application
) : BaseViewModel<SettingsState>(application) {

    override val initialState: SettingsState = SettingsState()
    private val settingsStorage = (application as App).settingsStorage

    init {
        updateTheme()
    }

    fun getCurrentTheme(): Theme = state.value.theme

    fun setTheme(theme: Theme) = viewModelScope.launch {
        val isThemeSaved = settingsStorage.saveTheme(theme)
        if (isThemeSaved) {
            AppCompatDelegate.setDefaultNightMode(theme.systemUiMode)
            setState { copy(theme = theme) }
        }
    }

    private fun updateTheme() = viewModelScope.launch {
        val theme = settingsStorage.getTheme()
        setState { copy(theme = theme) }
    }
}