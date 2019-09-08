package com.ptbc.kotlin_mvvm.ui.feed.blogs

import androidx.recyclerview.widget.LinearLayoutManager
import dagger.Module
import dagger.Provides


@Module
class BlogFragmentModule {

    @Provides
    internal fun provideBlogAdapter(): BlogAdapter {
        return BlogAdapter(ArrayList())
    }

    @Provides
    internal fun provideLinearLayoutManager(fragment: BlogFragment): LinearLayoutManager {
        return LinearLayoutManager(fragment.activity)
    }
}