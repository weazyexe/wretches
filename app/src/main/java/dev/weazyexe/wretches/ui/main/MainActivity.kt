package dev.weazyexe.wretches.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.WindowCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dev.chrisbanes.insetter.applyInsetter
import dev.weazyexe.wretches.R
import dev.weazyexe.wretches.databinding.ActivityMainBinding
import dev.weazyexe.wretches.ui.main.adapter.CrimeAdapter
import dev.weazyexe.wretches.ui.newcrime.NewCrimeActivity
import dev.weazyexe.wretches.ui.settings.SettingsActivity
import kotlinx.coroutines.flow.collectLatest

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<MainViewModel>()

    private val adapter = CrimeAdapter {
        openActivity<NewCrimeActivity>(NewCrimeActivity.composeParams(it))
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
        crimesRv.applyInsetter {
            type(navigationBars = true) { padding() }
        }
        newCrimeButton.applyInsetter {
            type(tappableElement = true) { margin() }
        }
    }

    private fun initViews() = with(binding) {
        crimesRv.adapter = adapter
        crimesRv.layoutManager = LinearLayoutManager(this@MainActivity)
    }

    private fun initListeners() = with(binding) {
        newCrimeButton.setOnClickListener {
            openActivity<NewCrimeActivity>()
        }
        toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.settings) {
                openActivity<SettingsActivity>()
                true
            } else {
                false
            }
        }
    }

    private fun updateUi() = with(binding) {
        lifecycleScope.launchWhenStarted {
            viewModel.state.collectLatest {
                val hasCrimes = it.crimes.isNotEmpty()
                crimesRv.isVisible = hasCrimes
                emptyLayout.root.isVisible = !hasCrimes
                adapter.submitList(it.crimes)
            }
        }
    }

    private inline fun <reified A : Activity> openActivity(bundle: Bundle = bundleOf()) {
        Intent(this@MainActivity, A::class.java).apply {
            putExtras(bundle)
            startActivity(this)
        }
    }
}