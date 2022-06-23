package dev.weazyexe.wretches.ui.common

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<S>(application: Application) : AndroidViewModel(application) {

    private val _state by lazy { MutableStateFlow(initialState) }
    val state: StateFlow<S>
        get() = _state.asStateFlow()

    abstract val initialState: S

    protected fun setState(block: S.() -> S) = viewModelScope.launch {
        val currentState = state.value
        _state.emit(block(currentState))
    }
}