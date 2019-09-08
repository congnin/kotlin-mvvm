package com.ptbc.kotlin_mvvm.ui.main.rating

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class RateUseDialogProvider {

    @ContributesAndroidInjector
    abstract fun provideRateUsDialogFactory(): RateUsDialog
}