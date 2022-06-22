package dev.weazyexe.wretches.ui.newcrime

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.weazyexe.wretches.R
import dev.weazyexe.wretches.entity.Crime
import dev.weazyexe.wretches.ui.newcrime.NewCrimeActivity.Companion.EXTRA_CRIME
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NewCrimeViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    private val _state = MutableStateFlow(NewCrimeState())
    val state: StateFlow<NewCrimeState>
        get() = _state.asStateFlow()

    init {
        savedStateHandle.get<Crime>(EXTRA_CRIME)?.let { setCrime(it) }
    }

    fun updateTitle(title: String) {
        setState { copy(title = title) }
    }

    fun updateDescription(description: String) {
        setState { copy(description = description) }
    }

    fun updateSolved(isSolved: Boolean) {
        setState { copy(isSolved = isSolved) }
    }

    fun addPhoto(photo: Uri) {
        setState {
            copy(photos = photos + photo)
        }
    }

    private fun setCrime(crime: Crime) {
        setState {
            copy(
                toolbarTitleRes = R.string.new_crime_edit_text,
                id = crime.id,
                title = crime.title,
                description = crime.description,
                isSolved = crime.isSolved,
                photos = crime.photos
            )
        }
    }

    private fun setState(block: NewCrimeState.() -> NewCrimeState) = viewModelScope.launch {
        val currentState = state.value
        _state.emit(block(currentState))
    }
}