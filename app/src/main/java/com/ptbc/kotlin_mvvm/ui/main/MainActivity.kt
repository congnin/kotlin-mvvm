package com.ptbc.kotlin_mvvm.ui.main

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Animatable
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.navigation.NavigationView
import com.mindorks.placeholderview.SwipeDecor
import com.mindorks.placeholderview.SwipePlaceHolderView
import com.ptbc.kotlin_mvvm.BR
import com.ptbc.kotlin_mvvm.BuildConfig
import com.ptbc.kotlin_mvvm.R
import com.ptbc.kotlin_mvvm.ViewModelProviderFactory
import com.ptbc.kotlin_mvvm.data.model.other.QuestionCardData
import com.ptbc.kotlin_mvvm.databinding.ActivityMainBinding
import com.ptbc.kotlin_mvvm.databinding.NavHeaderMainBinding
import com.ptbc.kotlin_mvvm.ui.about.AboutFragment
import com.ptbc.kotlin_mvvm.ui.base.BaseActivity
import com.ptbc.kotlin_mvvm.ui.feed.FeedActivity
import com.ptbc.kotlin_mvvm.ui.login.LoginActivity
import com.ptbc.kotlin_mvvm.ui.main.rating.RateUsDialog
import com.ptbc.kotlin_mvvm.utils.ScreenUtils
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(), MainNavigator, HasSupportFragmentInjector {

    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    @Inject
    lateinit var factory: ViewModelProviderFactory
    private var mActivityMainBinding: ActivityMainBinding? = null
    private var mCardsContainerView: SwipePlaceHolderView? = null
    private var mDrawer: DrawerLayout? = null
    lateinit var mMainViewModel: MainViewModel
    private var mNavigationView: NavigationView? = null
    private var mToolbar: Toolbar? = null

    override val bindingVariable: Int
        get() = BR.viewModel

    override val layoutId: Int
        get() = R.layout.activity_main

    override val viewModel: MainViewModel
        get() {
            mMainViewModel = ViewModelProviders.of(this, factory).get(MainViewModel::class.java)
            return mMainViewModel
        }

    override fun handleError(throwable: Throwable) {
        // handle error
    }

    override fun onBackPressed() {
        val fragmentManager = supportFragmentManager
        val fragment = fragmentManager.findFragmentByTag(AboutFragment.TAG)
        if (fragment == null) {
            super.onBackPressed()
        } else {
            onFragmentDetached(AboutFragment.TAG)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onFragmentDetached(tag: String) {
        val fragmentManager = supportFragmentManager
        val fragment = fragmentManager.findFragmentByTag(tag)
        if (fragment != null) {
            fragmentManager
                .beginTransaction()
                .disallowAddToBackStack()
                .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
                .remove(fragment)
                .commitNow()
            unlockDrawer()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val drawable = item.icon
        if (drawable is Animatable) {
            (drawable as Animatable).start()
        }
        when (item.itemId) {
            R.id.action_cut -> return true
            R.id.action_copy -> return true
            R.id.action_share -> return true
            R.id.action_delete -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun openLoginActivity() {
        startActivity(LoginActivity.newIntent(this))
        finish()
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment>? {
        return fragmentDispatchingAndroidInjector
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityMainBinding = viewDataBinding
        mMainViewModel.navigator = this
        setUp()
    }

    override fun onResume() {
        super.onResume()
        if (mDrawer != null) {
            mDrawer!!.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        }
    }

    private fun lockDrawer() {
        if (mDrawer != null) {
            mDrawer!!.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        }
    }

    private fun setUp() {
        mDrawer = mActivityMainBinding!!.drawerView
        mToolbar = mActivityMainBinding!!.toolbar
        mNavigationView = mActivityMainBinding!!.navigationView
        mCardsContainerView = mActivityMainBinding!!.cardsContainer

        setSupportActionBar(mToolbar)
        val mDrawerToggle = object : ActionBarDrawerToggle(
            this,
            mDrawer,
            mToolbar,
            R.string.open_drawer,
            R.string.close_drawer
        ) {

            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                hideKeyboard()
            }
        }
        mDrawer!!.addDrawerListener(mDrawerToggle)
        mDrawerToggle.syncState()
        setupNavMenu()
        val version = getString(R.string.version) + " " + BuildConfig.VERSION_NAME
        mMainViewModel.updateAppVersion(version)
        mMainViewModel.onNavMenuCreated()
        setupCardContainerView()
        subscribeToLiveData()
    }

    private fun setupCardContainerView() {
        val screenWidth = ScreenUtils.getScreenWidth(this)
        val screenHeight = ScreenUtils.getScreenHeight(this)

        mCardsContainerView!!.builder
            .setDisplayViewCount(3)
            .setHeightSwipeDistFactor(10f)
            .setWidthSwipeDistFactor(5f)
            .setSwipeDecor(
                SwipeDecor()
                    .setViewWidth((0.90 * screenWidth).toInt())
                    .setViewHeight((0.75 * screenHeight).toInt())
                    .setPaddingTop(20)
                    .setSwipeRotationAngle(10)
                    .setRelativeScale(0.01f)
            )

        mCardsContainerView!!.addItemRemoveListener { count ->
            if (count == 0) {
                // reload the contents again after 1 sec delay
                Handler(mainLooper).postDelayed({
                    //Reload once all the cards are removed
                    mMainViewModel.loadQuestionCards()
                }, 800)
            } else {
                mMainViewModel.removeQuestionCard()
            }
        }
    }

    private fun setupNavMenu() {
        val navHeaderMainBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.nav_header_main, mActivityMainBinding?.navigationView, false
        ) as NavHeaderMainBinding
        mActivityMainBinding?.navigationView?.addHeaderView(navHeaderMainBinding.root)
        navHeaderMainBinding.viewModel = mMainViewModel

        mNavigationView?.setNavigationItemSelectedListener { item ->
            mDrawer!!.closeDrawer(GravityCompat.START)
            when (item.itemId) {
                R.id.navItemAbout -> {
                    showAboutFragment()
                    true
                }
                R.id.navItemRateUs -> {
                    RateUsDialog.newInstance().show(supportFragmentManager)
                    true
                }
                R.id.navItemFeed -> {
                    startActivity(FeedActivity.newIntent(this@MainActivity))
                    true
                }
                R.id.navItemLogout -> {
                    mMainViewModel.logout()
                    true
                }
                else -> false
            }
        }
    }

    private fun showAboutFragment() {
        lockDrawer()
        supportFragmentManager
            .beginTransaction()
            .disallowAddToBackStack()
            .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
            .add(R.id.clRootView, AboutFragment.newInstance(), AboutFragment.TAG)
            .commit()
    }

    private fun subscribeToLiveData() {
        mMainViewModel.getQuestionCardData()
            .observe(this,
                Observer<List<QuestionCardData>> { questionCardDatas -> mMainViewModel.setQuestionDataList(questionCardDatas!!) })
    }

    private fun unlockDrawer() {
        if (mDrawer != null) {
            mDrawer!!.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        }
    }

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}
