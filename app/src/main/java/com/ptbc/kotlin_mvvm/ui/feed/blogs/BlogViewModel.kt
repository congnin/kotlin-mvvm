package com.ptbc.kotlin_mvvm.ui.feed.blogs

import com.ptbc.kotlin_mvvm.data.model.api.BlogResponse
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ptbc.kotlin_mvvm.utils.rx.SchedulerProvider
import com.ptbc.kotlin_mvvm.data.DataManager
import com.ptbc.kotlin_mvvm.ui.base.BaseViewModel


class BlogViewModel(
    dataManager: DataManager,
    schedulerProvider: SchedulerProvider
) : BaseViewModel<BlogNavigator>(dataManager, schedulerProvider) {

    private val blogListLiveData: MutableLiveData<List<BlogResponse.Blog>> = MutableLiveData()

    init {
        fetchBlogs()
    }

    fun fetchBlogs() {
        setIsLoading(true)
        compositeDisposable.add(dataManager
            .blogApiCall
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe({ blogResponse ->
                if (blogResponse?.data != null) {
                    blogListLiveData.value = blogResponse.data
                }
                setIsLoading(false)
            }, { throwable ->
                setIsLoading(false)
                navigator!!.handleError(throwable)
            })
        )
    }

    fun getBlogListLiveData(): LiveData<List<BlogResponse.Blog>> {
        return blogListLiveData
    }
}