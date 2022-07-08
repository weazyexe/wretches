package dev.weazyexe.wretches.ui.main

import dev.weazyexe.wretches.entity.Crime

/**
 * Состояние экрана [MainActivity]
 */
data class MainState(
    val crimes: List<Crime> = emptyList()
)

/**
 * Сайд эффекты экрана [MainActivity]
 */
sealed class MainEffect