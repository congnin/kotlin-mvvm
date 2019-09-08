package com.ptbc.kotlin_mvvm.ui.feed

import dagger.Module
import dagger.Provides

@Module
class FeedActivityModule {

    @Provides
    internal fun provideFeedPagerAdapter(activity: FeedActivity): FeedPagerAdapter {
        return FeedPagerAdapter(activity.supportFragmentManager)
    }

}