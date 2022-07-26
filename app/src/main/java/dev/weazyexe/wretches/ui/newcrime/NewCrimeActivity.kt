package dev.weazyexe.wretches.ui.newcrime

import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.WindowCompat
import androidx.core.widget.doOnTextChanged
import dev.chrisbanes.insetter.applyInsetter
import dev.weazyexe.wretches.databinding.ActivityNewCrimeBinding
import dev.weazyexe.wretches.entity.Crime
import dev.weazyexe.wretches.ui.newcrime.NewCrimeEffect.*
import dev.weazyexe.wretches.ui.newcrime.adapter.PhotoAdapter
import dev.weazyexe.wretches.ui.newcrime.photopicker.PhotoPickerDialog
import dev.weazyexe.wretches.utils.subscribe
import dev.weazyexe.wretches.utils.updateIfNeeds

/**
 * Экран с добавлением/редактированием преступлений
 */
class NewCrimeActivity : AppCompatActivity() {

    private val binding by lazy { ActivityNewCrimeBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<NewCrimeViewModel>()

    private val adapter = PhotoAdapter(
        onCloseClick = { viewModel.removePhoto(it) }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initEdgeToEdge()
        initViews()
        initListeners()
        updateUi()
    }

    /**
     * Инициализация edge-to-edge режима
     */
    private fun initEdgeToEdge() = with(binding) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        toolbar.applyInsetter {
            type(statusBars = true) { padding() }
        }
        saveButton.applyInsetter {
            type(tappableElement = true, ime = true) { margin() }
        }
        pickPhotosButton.setOnClickListener {
            PhotoPickerDialog().show(supportFragmentManager, PhotoPickerDialog.TAG)
        }
    }

    private fun initViews() = with(binding) {
        photosRv.adapter = adapter
    }

    private fun initListeners() = with(binding) {
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        titleTv.doOnTextChanged { text, _, _, _ ->
            titleTil.isErrorEnabled = false
            viewModel.updateTitle(text.toString())
        }
        descriptionTv.doOnTextChanged { text, _, _, _ ->
            descriptionTil.isErrorEnabled = false
            viewModel.updateDescription(text.toString())
        }
        solvedCb.setOnCheckedChangeListener { _, isChecked ->
            viewModel.updateSolved(isChecked)
        }
        saveButton.setOnClickListener {
            viewModel.save()
        }
        supportFragmentManager.setFragmentResultListener(
            PhotoPickerDialog.PHOTO_PICKER_RESULT_KEY,
            this@NewCrimeActivity
        ) { _, bundle ->
            val result = bundle.getParcelable<Uri?>(PhotoPickerDialog.PHOTO_PICKER_URI)
            if (result != null) {
                viewModel.addPhoto(result)
            }
        }
    }

    /**
     * Подписываемся на обновление состояния из ViewModel
     */
    private fun updateUi() = with(binding) {
        subscribe(
            viewModel,
            onNewState = {
                toolbar.title = getString(it.toolbarTitleRes)
                titleTv.updateIfNeeds(it.title)
                descriptionTv.updateIfNeeds(it.description)
                solvedCb.updateIfNeeds(it.isSolved)
                adapter.submitList(it.photos)
            },
            onNewEffect = {
                when (it) {
                    is GoBack -> onBackPressedDispatcher.onBackPressed()
                    is SetTitleError -> {
                        titleTil.isErrorEnabled = true
                        titleTil.error = getString(it.resId)
                    }
                    is SetDescriptionError -> {
                        descriptionTil.isErrorEnabled = true
                        descriptionTil.error = getString(it.resId)
                    }
                }
            }
        )
    }

    companion object {

        const val EXTRA_CRIME = "EXTRA_CRIME"

        fun composeParams(crime: Crime): Bundle = bundleOf(
            EXTRA_CRIME to crime
        )
    }
}