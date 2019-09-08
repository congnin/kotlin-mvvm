package com.ptbc.kotlin_mvvm.ui.feed

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.ptbc.kotlin_mvvm.ui.feed.opensource.OpenSourceFragment
import com.ptbc.kotlin_mvvm.ui.feed.blogs.BlogFragment
import androidx.fragment.app.FragmentStatePagerAdapter


class FeedPagerAdapter(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager) {

    private var mTabCount: Int = 0

    init {
        this.mTabCount = 0
    }

    override fun getCount(): Int {
        return mTabCount
    }

    fun setCount(count: Int) {
        mTabCount = count
    }

    override fun getItem(position: Int): Fragment? {
        return when (position) {
            0 -> BlogFragment()
            1 -> OpenSourceFragment.newInstance()
            else -> null
        }
    }
}