package com.ptbc.kotlin_mvvm.ui.main

import android.text.TextUtils
import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.databinding.ObservableList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ptbc.kotlin_mvvm.data.DataManager
import com.ptbc.kotlin_mvvm.data.model.other.QuestionCardData
import com.ptbc.kotlin_mvvm.ui.base.BaseViewModel
import com.ptbc.kotlin_mvvm.utils.rx.SchedulerProvider

class MainViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<MainNavigator>(dataManager, schedulerProvider) {

    val appVersion = ObservableField<String>()

    private val questionCardData: MutableLiveData<List<QuestionCardData>>

    val questionDataList: ObservableList<QuestionCardData> = ObservableArrayList<QuestionCardData>()

    val userEmail = ObservableField<String>()

    val userName = ObservableField<String>()

    val userProfilePicUrl = ObservableField<String>()

    var action = NO_ACTION

    init {
        questionCardData = MutableLiveData<List<QuestionCardData>>()
        loadQuestionCards()
    }

    fun getQuestionCardData(): LiveData<List<QuestionCardData>> {
        return questionCardData
    }

    fun setQuestionDataList(questionCardDatas: List<QuestionCardData>) {
        action = ACTION_ADD_ALL
        questionDataList.clear()
        questionDataList.addAll(questionCardDatas)
    }

    fun loadQuestionCards() {
        compositeDisposable.add(dataManager
            .questionCardData
            .doOnNext { list -> Log.d(TAG, "loadQuestionCards: " + list.size) }
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe({ questionList ->
                if (questionList != null) {
                    Log.d(TAG, "loadQuestionCards: " + questionList.size)
                    action = ACTION_ADD_ALL
                    questionCardData.value = questionList
                }
            }, { throwable -> Log.d(TAG, "loadQuestionCards: $throwable") })
        )
    }

    fun logout() {
        setIsLoading(true)
        compositeDisposable.add(dataManager.doLogoutApiCall()
            .doOnSuccess { response -> dataManager.setUserAsLoggedOut() }
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe({ response ->
                setIsLoading(false)
                navigator?.openLoginActivity()
            }, { throwable ->
                setIsLoading(false)
                navigator?.handleError(throwable)
            })
        )
    }

    fun onNavMenuCreated() {
        val currentUserName = dataManager.currentUserName
        if (!TextUtils.isEmpty(currentUserName)) {
            userName.set(currentUserName)
        }

        val currentUserEmail = dataManager.currentUserEmail
        if (!TextUtils.isEmpty(currentUserEmail)) {
            userEmail.set(currentUserEmail)
        }

        val profilePicUrl = dataManager.currentUserProfilePicUrl
        if (!TextUtils.isEmpty(profilePicUrl)) {
            userProfilePicUrl.set(profilePicUrl)
        }
    }

    fun removeQuestionCard() {
        action = ACTION_DELETE_SINGLE
        (questionCardData.value!!as MutableList).removeAt(0)
    }

    fun updateAppVersion(version: String) {
        appVersion.set(version)
    }

    companion object {

        private const val TAG = "MainViewModel"

        const val NO_ACTION = -1
        const val ACTION_ADD_ALL = 0
        const val ACTION_DELETE_SINGLE = 1
    }
}