package dev.weazyexe.wretches.ui.settings

import android.Manifest
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.google.android.material.snackbar.Snackbar
import dev.chrisbanes.insetter.applyInsetter
import dev.weazyexe.wretches.R
import dev.weazyexe.wretches.databinding.ActivitySettingsBinding
import dev.weazyexe.wretches.utils.AlertDialogBuilder
import dev.weazyexe.wretches.utils.handlePermission
import dev.weazyexe.wretches.utils.subscribe

/**
 * Экран с настройками приложения
 */
class SettingsActivity : AppCompatActivity() {

    companion object {

        private const val FILE_NAME = "wretches_backup.json"
    }

    private val binding by lazy { ActivitySettingsBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<SettingsViewModel>()

    private val createFileLauncher =
        registerForActivityResult(
            ActivityResultContracts.CreateDocument("application/json")
        ) { uri -> uri?.let { viewModel.backup(uri) } }

    private val openFileLauncher =
        registerForActivityResult(
            ActivityResultContracts.OpenDocument()
        ) { uri -> uri?.let { viewModel.restore(uri) } }

    private val permissionsLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                createBackupFile()
            } else {
                AlertDialogBuilder.noWriteStoragePermission(this).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initEdgeToEdge()
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
    }

    private fun initListeners() = with(binding) {
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        themeButton.setOnClickListener {
            AlertDialogBuilder.themePicker(
                context = this@SettingsActivity,
                value = viewModel.getCurrentTheme(),
                onSuccess = { viewModel.setTheme(it) }
            ).show()
        }
        backupButton.setOnClickListener {
            handlePermission(
                permission = Manifest.permission.WRITE_EXTERNAL_STORAGE,
                onPermissionGranted = { createBackupFile() },
                onPermissionDenied = { permissionsLauncher.launch(it) }
            )
        }
        restoreButton.setOnClickListener {
            openFileLauncher.launch(arrayOf("application/json"))
        }
    }

    /**
     * Подписываемся на обновление состояния из ViewModel
     */
    private fun updateUi() = with(binding) {
        subscribe(
            viewModel,
            onNewState = {
                themeValueTv.text = getString(it.theme.stringRes)
            },
            onNewEffect = {
                when (it) {
                    is SettingsEffect.ShowSnackbar -> {
                        Snackbar.make(binding.root, it.messageRes, Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        )
    }

    private fun createBackupFile() {
        if (viewModel.hasCrimes()) {
            createFileLauncher.launch(FILE_NAME)
        } else {
            viewModel.emitEffect(SettingsEffect.ShowSnackbar(R.string.settings_backup_nothing_to_backup_text))
        }
    }
}