package com.ptbc.kotlin_mvvm.ui.main.rating

import com.ptbc.kotlin_mvvm.data.DataManager
import com.ptbc.kotlin_mvvm.ui.base.BaseViewModel
import com.ptbc.kotlin_mvvm.utils.rx.SchedulerProvider

class RateUsViewModel : BaseViewModel<RateUsCallback> {
    constructor(
        dataManager: DataManager,
        schedulerProvider: SchedulerProvider
    ) : super(dataManager, schedulerProvider)

    fun onLaterClick() {
        navigator?.dismissDialog()
    }

    fun onSubmitClick() {
        navigator?.dismissDialog()
    }
}