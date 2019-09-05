package com.ptbc.kotlin_mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ptbc.kotlin_mvvm.data.DataManager
import com.ptbc.kotlin_mvvm.ui.login.LoginViewModel
import com.ptbc.kotlin_mvvm.ui.main.MainViewModel
import com.ptbc.kotlin_mvvm.ui.splash.SplashViewModel
import com.ptbc.kotlin_mvvm.utils.rx.SchedulerProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ViewModelProviderFactory @Inject
constructor(
    private val dataManager: DataManager,
    private val schedulerProvider: SchedulerProvider
) : ViewModelProvider.NewInstanceFactory() {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(AboutViewModel::class.java!!)) {
//
//            return AboutViewModel(dataManager, schedulerProvider) as T
//        } else if (modelClass.isAssignableFrom(FeedViewModel::class.java!!)) {
//
//            return FeedViewModel(dataManager, schedulerProvider) as T
//        } else
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {

            return LoginViewModel(dataManager, schedulerProvider) as T
        } else if (modelClass.isAssignableFrom(MainViewModel::class.java!!)) {

            return MainViewModel(dataManager, schedulerProvider) as T
        }
//        else if (modelClass.isAssignableFrom(BlogViewModel::class.java!!)) {
//
//            return BlogViewModel(dataManager, schedulerProvider) as T
//        } else if (modelClass.isAssignableFrom(RateUsViewModel::class.java!!)) {
//
//            return RateUsViewModel(dataManager, schedulerProvider) as T
//        } else if (modelClass.isAssignableFrom(OpenSourceViewModel::class.java!!)) {
//
//            return OpenSourceViewModel(dataManager, schedulerProvider) as T
//        }
        else
            if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {

                return SplashViewModel(dataManager, schedulerProvider) as T
            }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}