package dev.weazyexe.wretches.ui.newcrime

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import dev.chrisbanes.insetter.applyInsetter
import dev.weazyexe.wretches.databinding.ActivityNewCrimeBinding

class NewCrimeActivity : AppCompatActivity() {

    private val binding by lazy { ActivityNewCrimeBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initEdgeToEdge()
        initListeners()
    }

    private fun initEdgeToEdge() = with(binding) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        toolbar.applyInsetter {
            type(navigationBars = true, statusBars = true) { padding() }
        }
        saveButton.applyInsetter {
            type(tappableElement = true, ime = true) { margin() }
        }
    }

    private fun initListeners() = with(binding) {
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}