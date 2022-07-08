package dev.weazyexe.wretches.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import dev.weazyexe.wretches.ui.common.BaseViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Подписаться на состояние и эффекты из [BaseViewModel]
 */
fun <S, E> LifecycleOwner.subscribe(
    viewModel: BaseViewModel<S, E>,
    onNewState: (S) -> Unit,
    onNewEffect: (E) -> Unit = {}
) {
    lifecycleScope.launchWhenResumed {
        launch {
            viewModel.state.collectLatest {
                onNewState(it)
            }
        }

        launch {
            viewModel.effects.collectLatest {
                onNewEffect(it)
            }
        }
    }
}