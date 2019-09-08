package com.ptbc.kotlin_mvvm.ui.feed.blogs

import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class BlogFragmentProvider {

    @ContributesAndroidInjector(modules = [BlogFragmentModule::class])
    internal abstract fun provideBlogFragmentFactory(): BlogFragment
}