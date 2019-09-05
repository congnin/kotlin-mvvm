package com.ptbc.kotlin_mvvm

import android.app.Activity
import android.app.Application
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.interceptors.HttpLoggingInterceptor
import com.ptbc.kotlin_mvvm.di.component.DaggerAppComponent
import com.ptbc.kotlin_mvvm.utils.AppLogger
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import javax.inject.Inject

class MyApp : Application(), HasActivityInjector {

    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var mCalligraphyConfig: CalligraphyConfig

    override fun activityInjector(): DispatchingAndroidInjector<Activity>? {
        return activityDispatchingAndroidInjector
    }

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)

        AppLogger.init()

        AndroidNetworking.initialize(applicationContext)
        if (BuildConfig.DEBUG) {
            AndroidNetworking.enableLogging(HttpLoggingInterceptor.Level.BODY)
        }

        CalligraphyConfig.initDefault(mCalligraphyConfig)
    }
}