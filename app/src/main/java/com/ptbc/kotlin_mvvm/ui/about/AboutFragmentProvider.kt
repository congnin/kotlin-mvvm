package com.ptbc.kotlin_mvvm.ui.about

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AboutFragmentProvider {

    @ContributesAndroidInjector
    internal abstract fun provideAboutFragmentFactory(): AboutFragment
}