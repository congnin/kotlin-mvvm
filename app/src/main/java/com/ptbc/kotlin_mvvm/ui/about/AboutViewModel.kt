package com.ptbc.kotlin_mvvm.ui.about

import com.ptbc.kotlin_mvvm.data.DataManager
import com.ptbc.kotlin_mvvm.ui.base.BaseViewModel
import com.ptbc.kotlin_mvvm.utils.rx.SchedulerProvider

class AboutViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<AboutNavigator>(dataManager, schedulerProvider) {

    fun onNavBackClick() {
        navigator?.goBack()
    }
}