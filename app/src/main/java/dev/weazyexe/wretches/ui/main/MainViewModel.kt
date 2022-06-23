package dev.weazyexe.wretches.ui.main

import android.app.Application
import dev.weazyexe.wretches.ui.common.BaseViewModel

class MainViewModel(application: Application) : BaseViewModel<MainState>(application) {

    override val initialState: MainState = MainState()
}