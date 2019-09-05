package com.ptbc.kotlin_mvvm.ui.about

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.ptbc.kotlin_mvvm.BR
import com.ptbc.kotlin_mvvm.R
import com.ptbc.kotlin_mvvm.ViewModelProviderFactory
import com.ptbc.kotlin_mvvm.databinding.FragmentAboutBinding
import com.ptbc.kotlin_mvvm.ui.base.BaseFragment
import javax.inject.Inject

class AboutFragment : BaseFragment<FragmentAboutBinding, AboutViewModel>(), AboutNavigator {
    @Inject
    lateinit var factory: ViewModelProviderFactory
    lateinit var mAboutViewModel: AboutViewModel

    override val bindingVariable: Int
        get() = BR.viewModel

    override val layoutId: Int
        get() = R.layout.fragment_about

    override val viewModel: AboutViewModel
        get() {
            mAboutViewModel = ViewModelProviders.of(this, factory).get(AboutViewModel::class.java)
            return mAboutViewModel
        }

    override fun goBack() {
        baseActivity?.onFragmentDetached(TAG)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAboutViewModel.navigator = this
    }

    companion object {

        val TAG: String = AboutFragment::class.java.simpleName

        fun newInstance(): AboutFragment {
            val args = Bundle()
            val fragment = AboutFragment()
            fragment.setArguments(args)
            return fragment
        }
    }
}