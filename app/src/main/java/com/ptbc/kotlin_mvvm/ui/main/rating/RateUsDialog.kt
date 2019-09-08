package com.ptbc.kotlin_mvvm.ui.main.rating

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import com.ptbc.kotlin_mvvm.R
import com.ptbc.kotlin_mvvm.ViewModelProviderFactory
import com.ptbc.kotlin_mvvm.databinding.DialogRateUsBinding
import com.ptbc.kotlin_mvvm.ui.base.BaseDialog
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class RateUsDialog : BaseDialog(), RateUsCallback {

    @Inject
    lateinit var factory: ViewModelProviderFactory
    lateinit var mRateUsViewModel: RateUsViewModel

    override fun dismissDialog() {
        dismissDialog(TAG)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: DialogRateUsBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_rate_us, container, false)
        val view = binding.root

        AndroidSupportInjection.inject(this)
        mRateUsViewModel = ViewModelProviders.of(this, factory).get(RateUsViewModel::class.java)
        binding.viewModel = mRateUsViewModel

        mRateUsViewModel.navigator = this
        return view
    }

    fun show(fragmentManager: FragmentManager) {
        super.showNotAddToBackStack(fragmentManager, TAG)
    }

    companion object {
        private const val TAG = "RateUsDialog"

        fun newInstance(): RateUsDialog {
            val fragment = RateUsDialog()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }
}