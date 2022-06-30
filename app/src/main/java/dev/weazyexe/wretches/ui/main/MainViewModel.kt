package dev.weazyexe.wretches.ui.main

import android.app.Application
import androidx.lifecycle.viewModelScope
import dev.weazyexe.wretches.app.App
import dev.weazyexe.wretches.ui.common.BaseViewModel
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : BaseViewModel<MainState>(application) {

    override val initialState: MainState = MainState()
    private val crimesStorage = (application as App).crimesStorage

    init {
        fetchCrimes()
    }

    private fun fetchCrimes() = viewModelScope.launch {
        val crimes = crimesStorage.getAll()
        setState { copy(crimes = crimes) }
    }
}