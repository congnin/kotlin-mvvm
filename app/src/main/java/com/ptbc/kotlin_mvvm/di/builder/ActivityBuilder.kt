package com.ptbc.kotlin_mvvm.di.builder

import com.ptbc.kotlin_mvvm.ui.about.AboutFragmentProvider
import com.ptbc.kotlin_mvvm.ui.login.LoginActivity
import com.ptbc.kotlin_mvvm.ui.main.MainActivity
import com.ptbc.kotlin_mvvm.ui.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector
    internal abstract fun bindLoginActivity(): LoginActivity

    @ContributesAndroidInjector(modules = [AboutFragmentProvider::class])
    internal abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    internal abstract fun bindSplashActivity(): SplashActivity
}