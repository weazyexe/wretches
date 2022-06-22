package dev.weazyexe.wretches.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.recyclerview.widget.LinearLayoutManager
import dev.chrisbanes.insetter.Insetter
import dev.chrisbanes.insetter.applyInsetter
import dev.weazyexe.wretches.databinding.ActivityMainBinding
import dev.weazyexe.wretches.ui.main.adapter.CrimeAdapter

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<MainViewModel>()

    private val adapter = CrimeAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initEdgeToEdge()
        initViews()
        updateData()
    }

    private fun initEdgeToEdge() = with(binding) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        toolbar.applyInsetter {
            type(navigationBars = true, statusBars = true) { padding() }
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

    private fun updateData() {
        adapter.submitList(viewModel.crimes)
    }
}