package dev.weazyexe.wretches.ui.main

import androidx.lifecycle.ViewModel
import dev.weazyexe.wretches.entity.Crime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {

    private val _state = MutableStateFlow(MainState())
    val state: StateFlow<MainState>
        get() = _state.asStateFlow()
}