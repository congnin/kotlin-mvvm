package com.ptbc.kotlin_mvvm.ui.base

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import com.ptbc.kotlin_mvvm.data.DataManager
import com.ptbc.kotlin_mvvm.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import java.lang.ref.WeakReference

abstract class BaseViewModel<N>(
    val dataManager: DataManager,
    val schedulerProvider: SchedulerProvider
) : ViewModel() {

    val isLoading = ObservableBoolean()

    val compositeDisposable: CompositeDisposable

    private var mNavigator: WeakReference<N?>? = null

    var navigator: N?
        get() = mNavigator?.get()
        set(navigator) {
            this.mNavigator = WeakReference(navigator)
        }

    init {
        this.compositeDisposable = CompositeDisposable()
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    fun setIsLoading(isLoading: Boolean) {
        this.isLoading.set(isLoading)
    }
}