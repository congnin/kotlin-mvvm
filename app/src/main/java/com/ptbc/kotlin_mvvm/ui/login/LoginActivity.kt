package com.ptbc.kotlin_mvvm.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.ptbc.kotlin_mvvm.BR
import com.ptbc.kotlin_mvvm.ui.main.MainActivity
import com.ptbc.kotlin_mvvm.R
import com.ptbc.kotlin_mvvm.ViewModelProviderFactory
import com.ptbc.kotlin_mvvm.databinding.ActivityLoginBinding
import com.ptbc.kotlin_mvvm.ui.base.BaseActivity
import javax.inject.Inject

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>(), LoginNavigator {

    @Inject
    lateinit var factory: ViewModelProviderFactory
    lateinit var mLoginViewModel: LoginViewModel
    private var mActivityLoginBinding: ActivityLoginBinding? = null

    override val bindingVariable: Int
        get() = BR.viewModel

    override val layoutId: Int
        get() = R.layout.activity_login

    override val viewModel: LoginViewModel
        get() {
            mLoginViewModel = ViewModelProviders.of(this, factory).get(LoginViewModel::class.java)
            return mLoginViewModel
        }

    override fun handleError(throwable: Throwable) {
        // handle error
    }

    override fun login() {
        val email = mActivityLoginBinding!!.etEmail.getText().toString()
        val password = mActivityLoginBinding!!.etPassword.getText().toString()
        if (mLoginViewModel.isEmailAndPasswordValid(email, password)) {
            hideKeyboard()
            mLoginViewModel.login(email, password)
        } else {
            Toast.makeText(this, getString(R.string.invalid_email_password), Toast.LENGTH_SHORT).show()
        }
    }

    override fun openMainActivity() {
        val intent = MainActivity.newIntent(this@LoginActivity)
        startActivity(intent)
        finish()
    }

    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityLoginBinding = viewDataBinding
        mLoginViewModel.navigator = this
    }

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }
}