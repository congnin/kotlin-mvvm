package com.ptbc.kotlin_mvvm.ui.feed.opensource

import androidx.lifecycle.MutableLiveData
import com.ptbc.kotlin_mvvm.data.DataManager
import com.ptbc.kotlin_mvvm.data.model.api.OpenSourceResponse
import com.ptbc.kotlin_mvvm.ui.base.BaseViewModel
import com.ptbc.kotlin_mvvm.utils.rx.SchedulerProvider
import io.reactivex.Observable
import io.reactivex.Single

class OpenSourceViewModel : BaseViewModel<OpenSourceNavigator> {

    val openSourceItemsLiveData: MutableLiveData<List<OpenSourceItemViewModel>> = MutableLiveData()

    constructor(
        dataManager: DataManager,
        schedulerProvider: SchedulerProvider
    ) : super(dataManager, schedulerProvider) {
        fetchRepos()
    }

    fun fetchRepos() {
        setIsLoading(true)
        compositeDisposable.add(dataManager
            .openSourceApiCall
            .map { openSourceResponse -> openSourceResponse.data }
            .flatMap { getViewModelList(it) }
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe({ openSourceResponse ->
                openSourceItemsLiveData.value = openSourceResponse
                setIsLoading(false)
            }, { throwable ->
                setIsLoading(false)
                navigator?.handleError(throwable)
            })
        )
    }

    private fun getViewModelList(repoList: List<OpenSourceResponse.Repo>): Single<List<OpenSourceItemViewModel>> {
        return Observable.fromIterable(repoList)
            .map { repo ->
                OpenSourceItemViewModel(
                    repo.coverImgUrl, repo.title,
                    repo.description, repo.projectUrl
                )
            }.toList()
    }
}