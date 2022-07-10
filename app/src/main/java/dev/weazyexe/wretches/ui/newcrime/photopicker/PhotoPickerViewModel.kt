package dev.weazyexe.wretches.ui.newcrime.photopicker

import android.app.Application
import androidx.lifecycle.viewModelScope
import dev.weazyexe.wretches.app.App
import dev.weazyexe.wretches.ui.common.BaseViewModel
import kotlinx.coroutines.launch

/**
 * Вью модель диалога [PhotoPickerDialog]
 */
class PhotoPickerViewModel(application: Application) :
    BaseViewModel<PhotoPickerState, PhotoPickerEffect>(application) {

    override val initialState: PhotoPickerState = PhotoPickerState()
    private val photoStorage = (application as App).photoStorage

    fun getPhotos() = viewModelScope.launch {
        val photos = photoStorage.getPhotos()
        setState { copy(photos = photos) }
    }
}