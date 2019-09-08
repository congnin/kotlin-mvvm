package com.ptbc.kotlin_mvvm.ui.feed.opensource

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class OpenSourceFragmentProvider {

    @ContributesAndroidInjector(modules = [OpenSourceFragmentModule::class])
    internal abstract fun provideOpenSourceFragmentFactory(): OpenSourceFragment
}