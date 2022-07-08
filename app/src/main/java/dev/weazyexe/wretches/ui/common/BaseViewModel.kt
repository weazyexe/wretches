package dev.weazyexe.wretches.ui.common

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * Базовая View Model для экранов приложения
 */
abstract class BaseViewModel<S, E>(application: Application) : AndroidViewModel(application) {

    /**
     * Состояние экрана
     */
    private val _state by lazy { MutableStateFlow(initialState) }
    val state: StateFlow<S>
        get() = _state.asStateFlow()

    /**
     * Сайд эффекты экрана
     */
    private val _effects = Channel<E>(Channel.BUFFERED)
    val effects: Flow<E>
        get() = _effects.receiveAsFlow()

    /**
     * Первоначальное состояние экрана
     */
    abstract val initialState: S

    /**
     * Обновить состояние экрана
     */
    protected fun setState(block: S.() -> S) = viewModelScope.launch {
        val currentState = state.value
        _state.emit(block(currentState))
    }

    /**
     * Отправить сайд эффект на экран
     */
    fun emitEffect(effect: E) = viewModelScope.launch {
        effect.emit()
    }

    /**
     * Отправить сайд эффект на экран. Удобный экстеншн для [E]
     */
    protected fun E.emit() = viewModelScope.launch {
        _effects.send(this@emit)
    }
}