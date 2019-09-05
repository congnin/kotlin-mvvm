package com.ptbc.kotlin_mvvm.ui.login

import android.text.TextUtils
import com.ptbc.kotlin_mvvm.data.DataManager
import com.ptbc.kotlin_mvvm.data.model.api.LoginRequest
import com.ptbc.kotlin_mvvm.ui.base.BaseViewModel
import com.ptbc.kotlin_mvvm.utils.CommonUtils
import com.ptbc.kotlin_mvvm.utils.rx.SchedulerProvider

class LoginViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<LoginNavigator>(dataManager, schedulerProvider) {

    fun isEmailAndPasswordValid(email: String, password: String): Boolean {
        // validate email and password
        if (TextUtils.isEmpty(email)) {
            return false
        }
        if (!CommonUtils.isEmailValid(email)) {
            return false
        }
        return !TextUtils.isEmpty(password)
    }

    fun login(email: String, password: String) {
        setIsLoading(true)
        compositeDisposable.add(dataManager
            .doServerLoginApiCall(LoginRequest.ServerLoginRequest(email, password))
            .doOnSuccess { response ->
                dataManager
                    .updateUserInfo(
                        response.accessToken,
                        response.userId,
                        DataManager.LoggedInMode.LOGGED_IN_MODE_SERVER,
                        response.userName,
                        response.userEmail,
                        response.googleProfilePicUrl
                    )
            }
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe({ response ->
                setIsLoading(false)
                navigator?.openMainActivity()
            }, { throwable ->
                setIsLoading(false)
                navigator?.handleError(throwable)
            })
        )
    }

    fun onFbLoginClick() {
        setIsLoading(true)
        compositeDisposable.add(dataManager
            .doFacebookLoginApiCall(LoginRequest.FacebookLoginRequest("test3", "test4"))
            .doOnSuccess { response ->
                dataManager
                    .updateUserInfo(
                        response.accessToken,
                        response.userId,
                        DataManager.LoggedInMode.LOGGED_IN_MODE_FB,
                        response.userName,
                        response.userEmail,
                        response.googleProfilePicUrl
                    )
            }
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe({ response ->
                setIsLoading(false)
                navigator?.openMainActivity()
            }, { throwable ->
                setIsLoading(false)
                navigator?.handleError(throwable)
            })
        )
    }

    fun onGoogleLoginClick() {
        setIsLoading(true)
        compositeDisposable.add(dataManager
            .doGoogleLoginApiCall(LoginRequest.GoogleLoginRequest("test1", "test1"))
            .doOnSuccess { response ->
                dataManager
                    .updateUserInfo(
                        response.accessToken,
                        response.userId,
                        DataManager.LoggedInMode.LOGGED_IN_MODE_GOOGLE,
                        response.userName,
                        response.userEmail,
                        response.googleProfilePicUrl
                    )
            }
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe({ response ->
                setIsLoading(false)
                navigator?.openMainActivity()
            }, { throwable ->
                setIsLoading(false)
                navigator?.handleError(throwable)
            })
        )
    }

    fun onServerLoginClick() {
        navigator?.login()
    }
}