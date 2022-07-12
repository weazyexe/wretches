package dev.weazyexe.wretches.ui.newcrime.photopicker

import android.app.Application
import android.net.Uri
import androidx.lifecycle.viewModelScope
import dev.weazyexe.wretches.ui.common.BaseViewModel
import kotlinx.coroutines.launch

/**
 * Вью модель диалога [PhotoPickerDialog]
 */
class PhotoPickerViewModel(application: Application) :
    BaseViewModel<PhotoPickerState, PhotoPickerEffect>(application) {

    override val initialState: PhotoPickerState = PhotoPickerState()

    fun getPhotos() = viewModelScope.launch {
        val photos = emptyList<Uri>() // TODO тянуть из PhotosStorage
        setState { copy(photos = photos) }
    }
}