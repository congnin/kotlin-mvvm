package com.ptbc.kotlin_mvvm.ui.feed.opensource

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.ptbc.kotlin_mvvm.BR
import com.ptbc.kotlin_mvvm.R
import com.ptbc.kotlin_mvvm.ViewModelProviderFactory
import com.ptbc.kotlin_mvvm.databinding.FragmentOpenSourceBinding
import com.ptbc.kotlin_mvvm.ui.base.BaseFragment
import javax.inject.Inject
import androidx.recyclerview.widget.DefaultItemAnimator

class OpenSourceFragment : BaseFragment<FragmentOpenSourceBinding, OpenSourceViewModel>(),
    OpenSourceNavigator, OpenSourceAdapter.OpenSourceAdapterListener {

    private var mFragmentOpenSourceBinding: FragmentOpenSourceBinding? = null
    @Inject
    lateinit var mLayoutManager: LinearLayoutManager
    @Inject
    lateinit var mOpenSourceAdapter: OpenSourceAdapter
    @Inject
    lateinit var factory: ViewModelProviderFactory
    lateinit var mOpenSourceViewModel: OpenSourceViewModel

    override val bindingVariable: Int
        get() = BR.viewModel

    override val layoutId: Int
        get() = R.layout.fragment_open_source

    override val viewModel: OpenSourceViewModel
        get() {
            mOpenSourceViewModel = ViewModelProviders.of(this, factory).get(OpenSourceViewModel::class.java)
            return mOpenSourceViewModel
        }

    override fun handleError(throwable: Throwable) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mOpenSourceViewModel.navigator = this
        mOpenSourceAdapter.setListener(this)
    }

    override fun onRetryClick() {
        mOpenSourceViewModel.fetchRepos()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mFragmentOpenSourceBinding = viewDataBinding
        setUp()
    }

    private fun setUp() {
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        mFragmentOpenSourceBinding?.openSourceRecyclerView?.layoutManager = mLayoutManager
        mFragmentOpenSourceBinding?.openSourceRecyclerView?.itemAnimator = DefaultItemAnimator()
        mFragmentOpenSourceBinding?.openSourceRecyclerView?.adapter = mOpenSourceAdapter
    }

    companion object {
        fun newInstance(): OpenSourceFragment {
            val args = Bundle();
            val fragment = OpenSourceFragment()
            fragment.arguments = args
            return fragment
        }
    }
}