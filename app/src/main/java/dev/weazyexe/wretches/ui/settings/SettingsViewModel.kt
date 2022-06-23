package dev.weazyexe.wretches.ui.settings

import android.app.Application
import androidx.lifecycle.viewModelScope
import dev.weazyexe.wretches.entity.Theme
import dev.weazyexe.wretches.ui.common.BaseViewModel
import kotlinx.coroutines.launch

class SettingsViewModel(
    application: Application
) : BaseViewModel<SettingsState>(application) {

    override val initialState: SettingsState = SettingsState()

    init {
        updateTheme()
    }

    fun getCurrentTheme(): Theme = state.value.theme

    fun setTheme(theme: Theme) = viewModelScope.launch {
        // TODO
    }

    private fun updateTheme() = viewModelScope.launch {
        // TODO
    }
}