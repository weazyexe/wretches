package dev.weazyexe.wretches.ui.main

import android.app.Application
import androidx.lifecycle.viewModelScope
import dev.weazyexe.wretches.entity.Crime
import dev.weazyexe.wretches.ui.common.BaseViewModel
import kotlinx.coroutines.launch

/**
 * Вью модель экрана [MainActivity]
 */
class MainViewModel(application: Application) : BaseViewModel<MainState, MainEffect>(application) {

    override val initialState: MainState = MainState()

    fun fetchCrimes() = viewModelScope.launch {
        val crimes = emptyList<Crime>()
        setState { copy(crimes = crimes) }
    }
}