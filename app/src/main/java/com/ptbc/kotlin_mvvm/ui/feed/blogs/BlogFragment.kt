package com.ptbc.kotlin_mvvm.ui.feed.blogs

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.ptbc.kotlin_mvvm.BR
import com.ptbc.kotlin_mvvm.R
import com.ptbc.kotlin_mvvm.ViewModelProviderFactory
import com.ptbc.kotlin_mvvm.data.model.api.BlogResponse
import com.ptbc.kotlin_mvvm.databinding.FragmentBlogBinding
import com.ptbc.kotlin_mvvm.ui.base.BaseFragment
import javax.inject.Inject

class BlogFragment : BaseFragment<FragmentBlogBinding, BlogViewModel>(), BlogNavigator,
    BlogAdapter.BlogAdapterListener {

    @Inject
    lateinit var mBlogAdapter: BlogAdapter
    private var mFragmentBlogBinding: FragmentBlogBinding? = null
    @Inject
    lateinit var mLayoutManager: LinearLayoutManager
    @Inject
    lateinit var factory: ViewModelProviderFactory
    lateinit var mBlogViewModel: BlogViewModel

    override val bindingVariable: Int
        get() = BR.viewModel

    override val layoutId: Int
        get() = R.layout.fragment_blog

    override val viewModel: BlogViewModel
        get() {
            mBlogViewModel = ViewModelProviders.of(this, factory).get(BlogViewModel::class.java)
            return mBlogViewModel
        }

    override fun handleError(throwable: Throwable) {
        // handle error
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBlogViewModel.navigator = this
        mBlogAdapter.setListener(this)
    }

    override fun onRetryClick() {
        mBlogViewModel.fetchBlogs()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mFragmentBlogBinding = viewDataBinding
        setUp()
    }

    override fun updateBlog(blogList: List<BlogResponse.Blog>?) {
        if (blogList != null) {
            mBlogAdapter.addItems(blogList)
        }
    }

    private fun setUp() {
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        mFragmentBlogBinding?.blogRecyclerView?.layoutManager = mLayoutManager
        mFragmentBlogBinding?.blogRecyclerView?.itemAnimator = DefaultItemAnimator()
        mFragmentBlogBinding?.blogRecyclerView?.adapter = mBlogAdapter
    }

    companion object {

        fun newInstance(): BlogFragment {
            val args = Bundle()
            val fragment = BlogFragment()
            fragment.arguments = args
            return fragment
        }
    }
}