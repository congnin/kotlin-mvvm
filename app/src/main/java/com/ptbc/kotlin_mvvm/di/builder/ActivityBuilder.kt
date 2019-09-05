package com.ptbc.kotlin_mvvm.di.builder

import com.ptbc.kotlin_mvvm.ui.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector
    internal abstract fun bindSplashActivity(): SplashActivity
}