package dev.weazyexe.wretches.ui.newcrime

import android.app.Application
import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import dev.weazyexe.wretches.R
import dev.weazyexe.wretches.entity.Crime
import dev.weazyexe.wretches.ui.common.BaseViewModel
import dev.weazyexe.wretches.ui.newcrime.NewCrimeActivity.Companion.EXTRA_CRIME

// Важен порядок - вначале application, потом savedStateHandle
class NewCrimeViewModel(
    application: Application,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<NewCrimeState>(application) {

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
}