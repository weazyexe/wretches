package dev.weazyexe.wretches.ui.newcrime.photopicker

import android.Manifest
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dev.chrisbanes.insetter.applyInsetter
import dev.weazyexe.wretches.R
import dev.weazyexe.wretches.databinding.BottomSheetPhotoPickerBinding
import dev.weazyexe.wretches.ui.newcrime.adapter.PhotoAdapter
import dev.weazyexe.wretches.utils.AlertDialogBuilder
import dev.weazyexe.wretches.utils.hasReadExternalStoragePermission
import dev.weazyexe.wretches.utils.subscribe

class PhotoPickerDialog : BottomSheetDialogFragment() {

    companion object {

        const val TAG = "PHOTO_PICKER_DIALOG_TAG"
        const val PHOTO_PICKER_RESULT_KEY = "PHOTO_PICKER_RESULT_KEY"
        const val PHOTO_PICKER_URI = "PHOTO_PICKER_URI"
    }

    private val binding by lazy { BottomSheetPhotoPickerBinding.bind(requireView()) }
    private val viewModel by viewModels<PhotoPickerViewModel>()

    private val permissionsLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                viewModel.getPhotos()
            } else {
                dismiss()
                AlertDialogBuilder.noReadStoragePermission(requireContext()).show()
            }
        }

    private val adapter = PhotoAdapter(onPhotoClick = { setResult(it) })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.bottom_sheet_photo_picker, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initEdgeToEdge()
        initViews()
        updateUi()

        loadPhotos()
    }

    private fun initEdgeToEdge() = with(binding) {
        root.applyInsetter {
            type(navigationBars = true) { padding() }
        }
    }

    private fun initViews() = with(binding) {
        photosRv.adapter = adapter
    }

    private fun updateUi() {
        subscribe(viewModel, onNewState = {
            adapter.submitList(it.photos)
        })
    }

    private fun loadPhotos() {
        if (requireContext().hasReadExternalStoragePermission()) {
            viewModel.getPhotos()
        } else {
            permissionsLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    private fun setResult(uri: Uri) {
        setFragmentResult(PHOTO_PICKER_RESULT_KEY, bundleOf(PHOTO_PICKER_URI to uri))
        dismiss()
    }
}