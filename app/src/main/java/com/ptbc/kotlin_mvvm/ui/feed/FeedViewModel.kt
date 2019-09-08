package com.ptbc.kotlin_mvvm.ui.feed

import com.ptbc.kotlin_mvvm.data.DataManager
import com.ptbc.kotlin_mvvm.ui.base.BaseViewModel
import com.ptbc.kotlin_mvvm.utils.rx.SchedulerProvider

class FeedViewModel : BaseViewModel<Nothing> {
    constructor(
        dataManager: DataManager,
        schedulerProvider: SchedulerProvider
    ) : super(dataManager, schedulerProvider)

}