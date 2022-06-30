package dev.weazyexe.wretches.ui.newcrime

import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import dev.chrisbanes.insetter.applyInsetter
import dev.weazyexe.wretches.databinding.ActivityNewCrimeBinding
import dev.weazyexe.wretches.entity.Crime
import dev.weazyexe.wretches.ui.newcrime.adapter.PhotoAdapter
import dev.weazyexe.wretches.utils.performIfChanged
import kotlinx.coroutines.flow.collectLatest

class NewCrimeActivity : AppCompatActivity() {

    private val binding by lazy { ActivityNewCrimeBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<NewCrimeViewModel>()

    private val adapter = PhotoAdapter {
        viewModel.removePhoto(it)
    }

    private val getPhotos = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let { viewModel.addPhoto(uri) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initEdgeToEdge()
        initViews()
        initListeners()
        updateUi()
    }

    private fun initEdgeToEdge() = with(binding) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        toolbar.applyInsetter {
            type(statusBars = true) { padding() }
        }
        saveButton.applyInsetter {
            type(tappableElement = true, ime = true) { margin() }
        }
        pickPhotosButton.setOnClickListener {
            getPhotos.launch("image/*")
        }
    }

    private fun initViews() = with(binding) {
        photosRv.adapter = adapter
    }

    private fun initListeners() = with(binding) {
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun updateUi() {
        with(binding) {
            lifecycleScope.launchWhenStarted {
                viewModel.state.collectLatest {
                    toolbar.title = getString(it.toolbarTitleRes)
                    titleTv.performIfChanged(it.title) { text = it.title }
                    descriptionTv.performIfChanged(it.description) { text = it.description }
                    solvedCb.performIfChanged(it.isSolved) { isChecked = it.isSolved }
                    adapter.submitList(it.photos)
                }
            }
        }
    }

    companion object {

        const val EXTRA_CRIME = "EXTRA_CRIME"

        fun composeParams(crime: Crime): Bundle = bundleOf(
            EXTRA_CRIME to crime
        )
    }
}