package dev.weazyexe.wretches.ui.newcrime

import android.app.Application
import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dev.weazyexe.wretches.R
import dev.weazyexe.wretches.entity.Crime
import dev.weazyexe.wretches.ui.common.BaseViewModel
import dev.weazyexe.wretches.ui.newcrime.NewCrimeActivity.Companion.EXTRA_CRIME
import dev.weazyexe.wretches.ui.newcrime.NewCrimeEffect.SetDescriptionError
import dev.weazyexe.wretches.ui.newcrime.NewCrimeEffect.SetTitleError
import kotlinx.coroutines.launch

/**
 * Вью модель экрана [NewCrimeActivity]
 * Важен порядок параметров в конструкторе - вначале application, потом savedStateHandle
 */
class NewCrimeViewModel(
    application: Application,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<NewCrimeState, NewCrimeEffect>(application) {

    override val initialState: NewCrimeState = NewCrimeState()

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

    fun removePhoto(photo: Uri) {
        setState {
            copy(photos = photos - photo)
        }
    }

    fun save() = viewModelScope.launch {
        if (validate()) {
            // TODO добавить сохранение преступления в БД
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

    private fun validate(): Boolean {
        if (state.value.title.isEmpty()) {
            SetTitleError(R.string.new_crime_field_must_not_be_empty_text).emit()
            return false
        }

        if (state.value.description.isEmpty()) {
            SetDescriptionError(R.string.new_crime_field_must_not_be_empty_text).emit()
            return false
        }

        return true
    }
}