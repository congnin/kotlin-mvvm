package com.ptbc.kotlin_mvvm.ui.feed.blogs

import com.ptbc.kotlin_mvvm.ui.base.BaseViewHolder
import com.ptbc.kotlin_mvvm.utils.AppLogger
import android.content.Intent
import android.net.Uri
import com.ptbc.kotlin_mvvm.data.model.api.BlogResponse
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ptbc.kotlin_mvvm.databinding.ItemBlogEmptyViewBinding
import com.ptbc.kotlin_mvvm.databinding.ItemBlogViewBinding


class BlogAdapter(private val mBlogResponseList: MutableList<BlogResponse.Blog>?) :
    RecyclerView.Adapter<BaseViewHolder>() {

    private var mListener: BlogAdapterListener? = null

    override fun getItemCount(): Int {
        return if (mBlogResponseList != null && mBlogResponseList.size > 0) {
            mBlogResponseList.size
        } else {
            1
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (mBlogResponseList != null && !mBlogResponseList.isEmpty()) {
            VIEW_TYPE_NORMAL
        } else {
            VIEW_TYPE_EMPTY
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        when (viewType) {
            VIEW_TYPE_NORMAL -> {
                val blogViewBinding = ItemBlogViewBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
                return BlogViewHolder(blogViewBinding)
            }
            VIEW_TYPE_EMPTY -> {
                val emptyViewBinding = ItemBlogEmptyViewBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
                return EmptyViewHolder(emptyViewBinding)
            }
            else -> {
                val emptyViewBinding = ItemBlogEmptyViewBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return EmptyViewHolder(emptyViewBinding)
            }
        }
    }

    fun addItems(blogList: List<BlogResponse.Blog>) {
        mBlogResponseList!!.addAll(blogList)
        notifyDataSetChanged()
    }

    fun clearItems() {
        mBlogResponseList!!.clear()
    }

    fun setListener(listener: BlogAdapterListener) {
        this.mListener = listener
    }

    interface BlogAdapterListener {

        fun onRetryClick()
    }

    inner class BlogViewHolder(private val mBinding: ItemBlogViewBinding) :
        BaseViewHolder(mBinding.root), BlogItemViewModel.BlogItemViewModelListener {

        private var mBlogItemViewModel: BlogItemViewModel? = null

        override fun onBind(position: Int) {
            val blog = mBlogResponseList!![position]
            mBlogItemViewModel = BlogItemViewModel(blog, this)
            mBinding.viewModel = mBlogItemViewModel

            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.
            mBinding.executePendingBindings()
        }

        override fun onItemClick(blogUrl: String?) {
            if (blogUrl != null) {
                try {
                    val intent = Intent()
                    intent.action = Intent.ACTION_VIEW
                    intent.addCategory(Intent.CATEGORY_BROWSABLE)
                    intent.data = Uri.parse(blogUrl)
                    itemView.context.startActivity(intent)
                } catch (e: Exception) {
                    AppLogger.d("url error")
                }

            }
        }
    }

    inner class EmptyViewHolder(private val mBinding: ItemBlogEmptyViewBinding) :
        BaseViewHolder(mBinding.root), BlogEmptyItemViewModel.BlogEmptyItemViewModelListener {

        override fun onBind(position: Int) {
            val emptyItemViewModel = BlogEmptyItemViewModel(this)
            mBinding.viewModel = emptyItemViewModel
        }

        override fun onRetryClick() {
            mListener!!.onRetryClick()
        }
    }

    companion object {

        const val VIEW_TYPE_EMPTY = 0

        const val VIEW_TYPE_NORMAL = 1
    }
}