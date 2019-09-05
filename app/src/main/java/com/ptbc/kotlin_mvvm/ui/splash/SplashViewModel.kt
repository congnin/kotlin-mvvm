package com.ptbc.kotlin_mvvm.ui.splash

import com.ptbc.kotlin_mvvm.data.DataManager
import com.ptbc.kotlin_mvvm.ui.base.BaseViewModel
import com.ptbc.kotlin_mvvm.utils.AppLogger
import com.ptbc.kotlin_mvvm.utils.rx.SchedulerProvider

class SplashViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<SplashNavigator>(dataManager, schedulerProvider) {

    fun startSeeding() {
        compositeDisposable.add(
            dataManager
                .seedDatabaseQuestions()
                .flatMap { aBoolean -> dataManager.seedDatabaseOptions() }
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(
                    { aBoolean ->
                        decideNextActivity()
                        AppLogger.i("SplashViewModel $aBoolean")
                        getAllQuestion()
                    },
                    { throwable ->
                        decideNextActivity()
                        AppLogger.d(throwable, "SplashViewModel")
                    })
        )
    }

    private fun decideNextActivity() {
        if (dataManager.currentUserLoggedInMode == DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT.type) {
            navigator?.openLoginActivity()
        } else {
            navigator?.openMainActivity()
        }
    }

    fun getAllQuestion() {
        compositeDisposable.add(
            dataManager.allQuestions
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({ listQuestion ->
                    AppLogger.i("SplashViewModel ${listQuestion[0].questionText}")
                },
                    { throwable ->
                        AppLogger.d(throwable, "SplashViewModel")
                    })
        )
    }
}