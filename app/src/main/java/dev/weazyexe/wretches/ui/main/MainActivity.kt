package dev.weazyexe.wretches.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.recyclerview.widget.LinearLayoutManager
import dev.chrisbanes.insetter.applyInsetter
import dev.weazyexe.wretches.databinding.ActivityMainBinding
import dev.weazyexe.wretches.ui.main.adapter.CrimeAdapter
import dev.weazyexe.wretches.ui.newcrime.NewCrimeActivity

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<MainViewModel>()

    private val adapter = CrimeAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initEdgeToEdge()
        initViews()
        initListeners()
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

    private fun initListeners() = with(binding) {
        newCrimeButton.setOnClickListener {
            openActivity<NewCrimeActivity>()
        }
    }

    private fun updateData() {
        adapter.submitList(viewModel.crimes)
    }

    private inline fun <reified A : Activity> openActivity() {
        Intent(this@MainActivity, A::class.java).apply {
            startActivity(this)
        }
    }
}