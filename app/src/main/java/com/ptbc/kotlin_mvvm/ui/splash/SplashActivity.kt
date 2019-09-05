package com.ptbc.kotlin_mvvm.ui.splash

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.ptbc.kotlin_mvvm.BR
import com.ptbc.kotlin_mvvm.R
import com.ptbc.kotlin_mvvm.ViewModelProviderFactory
import com.ptbc.kotlin_mvvm.databinding.ActivitySplashBinding
import com.ptbc.kotlin_mvvm.ui.base.BaseActivity
import javax.inject.Inject

class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>(), SplashNavigator {

    @Inject
    lateinit var factory: ViewModelProviderFactory

    lateinit var mSplashViewModel: SplashViewModel

    override val bindingVariable: Int
        get() = BR.viewModel

    override val layoutId: Int
        get() = R.layout.activity_splash

    override val viewModel: SplashViewModel
        get() = ViewModelProviders.of(this, factory).get(SplashViewModel::class.java)

    override fun openLoginActivity() {

    }

    override fun openMainActivity() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSplashViewModel = ViewModelProviders.of(this, factory).get(SplashViewModel::class.java)
        mSplashViewModel.navigator = this
        mSplashViewModel.startSeeding()
    }
}