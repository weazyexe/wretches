package dev.weazyexe.wretches.ui.settings

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import dev.chrisbanes.insetter.applyInsetter
import dev.weazyexe.wretches.databinding.ActivitySettingsBinding
import dev.weazyexe.wretches.entity.Theme
import dev.weazyexe.wretches.utils.AlertDialogBuilder

class SettingsActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySettingsBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initEdgeToEdge()
        initListeners()
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
            AlertDialogBuilder.themePicker(this@SettingsActivity, Theme.SYSTEM) {
                Toast.makeText(this@SettingsActivity, it.stringRes, Toast.LENGTH_SHORT).show()
            }.show()
        }
    }
}