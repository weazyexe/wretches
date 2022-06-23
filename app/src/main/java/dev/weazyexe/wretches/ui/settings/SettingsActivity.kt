package dev.weazyexe.wretches.ui.settings

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import dev.chrisbanes.insetter.applyInsetter
import dev.weazyexe.wretches.databinding.ActivitySettingsBinding
import dev.weazyexe.wretches.utils.AlertDialogBuilder
import kotlinx.coroutines.flow.collectLatest

class SettingsActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySettingsBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<SettingsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initEdgeToEdge()
        initListeners()
        updateUi()
    }

    private fun initEdgeToEdge() = with(binding) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        toolbar.applyInsetter {
            type(statusBars = true) { padding() }
        }
    }

    private fun initListeners() = with(binding) {
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        themeButton.setOnClickListener {
            AlertDialogBuilder.themePicker(
                context = this@SettingsActivity,
                value = viewModel.getCurrentTheme(),
                onSuccess = { viewModel.setTheme(it) }
            ).show()
        }
    }

    private fun updateUi() = with(binding) {
        lifecycleScope.launchWhenStarted {
            viewModel.state.collectLatest {
                themeValueTv.text = getString(it.theme.stringRes)
            }
        }
    }
}