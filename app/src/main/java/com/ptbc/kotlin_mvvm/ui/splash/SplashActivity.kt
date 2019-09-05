package com.ptbc.kotlin_mvvm.ui.splash

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.ptbc.kotlin_mvvm.BR
import com.ptbc.kotlin_mvvm.ui.main.MainActivity
import com.ptbc.kotlin_mvvm.R
import com.ptbc.kotlin_mvvm.ViewModelProviderFactory
import com.ptbc.kotlin_mvvm.databinding.ActivitySplashBinding
import com.ptbc.kotlin_mvvm.ui.base.BaseActivity
import com.ptbc.kotlin_mvvm.ui.login.LoginActivity
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
        get() {
            mSplashViewModel = ViewModelProviders.of(this, factory).get(SplashViewModel::class.java)
            return mSplashViewModel
        }

    override fun openLoginActivity() {
        val intent = LoginActivity.newIntent(this@SplashActivity)
        startActivity(intent)
        finish()
    }

    override fun openMainActivity() {
        val intent = MainActivity.newIntent(this@SplashActivity)
        startActivity(intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSplashViewModel.navigator = this
        mSplashViewModel.startSeeding()
    }
}