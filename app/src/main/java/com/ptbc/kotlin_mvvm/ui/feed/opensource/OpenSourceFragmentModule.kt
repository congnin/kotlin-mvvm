package com.ptbc.kotlin_mvvm.ui.feed.opensource

import androidx.recyclerview.widget.LinearLayoutManager
import dagger.Module
import dagger.Provides

@Module
class OpenSourceFragmentModule {

    @Provides
    internal fun provideLinearLayoutManager(fragment: OpenSourceFragment): LinearLayoutManager {
        return LinearLayoutManager(fragment.activity)
    }

    @Provides
    internal fun provideOpenSourceAdapter(): OpenSourceAdapter {
        return OpenSourceAdapter()
    }

}