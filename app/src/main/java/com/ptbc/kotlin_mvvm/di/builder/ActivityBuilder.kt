package com.ptbc.kotlin_mvvm.di.builder

import com.ptbc.kotlin_mvvm.ui.about.AboutFragmentProvider
import com.ptbc.kotlin_mvvm.ui.feed.FeedActivity
import com.ptbc.kotlin_mvvm.ui.feed.FeedActivityModule
import com.ptbc.kotlin_mvvm.ui.feed.blogs.BlogFragmentProvider
import com.ptbc.kotlin_mvvm.ui.feed.opensource.OpenSourceFragmentProvider
import com.ptbc.kotlin_mvvm.ui.login.LoginActivity
import com.ptbc.kotlin_mvvm.ui.main.MainActivity
import com.ptbc.kotlin_mvvm.ui.main.rating.RateUseDialogProvider
import com.ptbc.kotlin_mvvm.ui.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(
        modules = [FeedActivityModule::class,
            BlogFragmentProvider::class, OpenSourceFragmentProvider::class]
    )
    internal abstract fun bindFeedActivity(): FeedActivity

    @ContributesAndroidInjector
    internal abstract fun bindLoginActivity(): LoginActivity

    @ContributesAndroidInjector(modules = [AboutFragmentProvider::class, RateUseDialogProvider::class])
    internal abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    internal abstract fun bindSplashActivity(): SplashActivity
}