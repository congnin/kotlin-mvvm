package com.ptbc.kotlin_mvvm.ui.feed

import android.content.Context
import androidx.fragment.app.Fragment
import com.ptbc.kotlin_mvvm.databinding.ActivityFeedBinding
import com.ptbc.kotlin_mvvm.ui.base.BaseActivity
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject
import com.ptbc.kotlin_mvvm.ViewModelProviderFactory
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.ViewModelProviders
import com.ptbc.kotlin_mvvm.BR
import com.ptbc.kotlin_mvvm.R
import androidx.core.app.NavUtils
import androidx.core.app.TaskStackBuilder
import dagger.android.AndroidInjector
import com.google.android.material.tabs.TabLayout


class FeedActivity : BaseActivity<ActivityFeedBinding, FeedViewModel>(),
    HasSupportFragmentInjector {
    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    @Inject
    lateinit var mPagerAdapter: FeedPagerAdapter
    @Inject
    lateinit var factory: ViewModelProviderFactory
    private var mActivityFeedBinding: ActivityFeedBinding? = null
    lateinit var mFeedViewModel: FeedViewModel

    override val bindingVariable: Int
        get() = BR.viewModel

    override val layoutId: Int
        get() = R.layout.activity_feed

    override val viewModel: FeedViewModel
        get() {
            mFeedViewModel = ViewModelProviders.of(this, factory).get(FeedViewModel::class.java)
            return mFeedViewModel
        }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            // Respond to the action bar's Up/Home button
            android.R.id.home -> {
                val upIntent = NavUtils.getParentActivityIntent(this)
                upIntent!!.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    // This activity is NOT part of this app's task, so create a new task
                    // when navigating up, with a synthesized back stack.
                    TaskStackBuilder.create(this)
                        // Add all of this activity's parents to the back stack
                        .addNextIntentWithParentStack(upIntent)
                        // Navigate up to the closest parent
                        .startActivities()
                } else {
                    // This activity is part of this app's task, so simply
                    // navigate up to the logical parent activity.
                    NavUtils.navigateUpTo(this, upIntent)
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentDispatchingAndroidInjector
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityFeedBinding = viewDataBinding
        setUp()
    }

    private fun setUp() {
        setSupportActionBar(mActivityFeedBinding?.toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
        }

        mPagerAdapter.count = 2

        mActivityFeedBinding?.feedViewPager?.adapter = mPagerAdapter

        mActivityFeedBinding?.tabLayout?.addTab(
            mActivityFeedBinding?.tabLayout!!.newTab().setText(
                getString(R.string.blog)
            )
        )
        mActivityFeedBinding?.tabLayout?.addTab(
            mActivityFeedBinding?.tabLayout!!.newTab().setText(
                getString(R.string.open_source)
            )
        )

        mActivityFeedBinding?.feedViewPager?.offscreenPageLimit =
            mActivityFeedBinding?.tabLayout!!.tabCount

        mActivityFeedBinding?.feedViewPager?.addOnPageChangeListener(
            TabLayout.TabLayoutOnPageChangeListener(
                mActivityFeedBinding!!.tabLayout
            )
        )

        mActivityFeedBinding?.tabLayout?.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab) {

            }

            override fun onTabSelected(tab: TabLayout.Tab) {
                mActivityFeedBinding?.feedViewPager?.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }
        })
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, FeedActivity::class.java)
        }
    }
}