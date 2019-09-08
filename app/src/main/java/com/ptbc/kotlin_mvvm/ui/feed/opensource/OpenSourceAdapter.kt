package com.ptbc.kotlin_mvvm.ui.feed.opensource

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ptbc.kotlin_mvvm.databinding.ItemOpenSourceEmptyViewBinding
import com.ptbc.kotlin_mvvm.databinding.ItemOpenSourceViewBinding
import com.ptbc.kotlin_mvvm.ui.base.BaseViewHolder
import com.ptbc.kotlin_mvvm.utils.AppLogger
import java.util.ArrayList

class OpenSourceAdapter : RecyclerView.Adapter<BaseViewHolder>() {

    private val mOpenSourceResponseList: MutableList<OpenSourceItemViewModel>

    private var mListener: OpenSourceAdapterListener? = null

    init {
        this.mOpenSourceResponseList = ArrayList()
    }

    override fun getItemCount(): Int {
        return if (!mOpenSourceResponseList.isEmpty()) {
            mOpenSourceResponseList.size
        } else {
            1
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (!mOpenSourceResponseList.isEmpty()) {
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
                val openSourceViewBinding = ItemOpenSourceViewBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                return OpenSourceViewHolder(openSourceViewBinding)
            }
            VIEW_TYPE_EMPTY -> {
                val emptyViewBinding = ItemOpenSourceEmptyViewBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                return EmptyViewHolder(emptyViewBinding)
            }
            else -> {
                val emptyViewBinding = ItemOpenSourceEmptyViewBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return EmptyViewHolder(emptyViewBinding)
            }
        }
    }

    fun addItems(repoList: List<OpenSourceItemViewModel>) {
        mOpenSourceResponseList.addAll(repoList)
        notifyDataSetChanged()
    }

    fun clearItems() {
        mOpenSourceResponseList.clear()
    }

    fun setListener(listener: OpenSourceAdapterListener) {
        this.mListener = listener
    }

    interface OpenSourceAdapterListener {

        fun onRetryClick()
    }

    inner class EmptyViewHolder(private val mBinding: ItemOpenSourceEmptyViewBinding) :
        BaseViewHolder(mBinding.root),
        OpenSourceEmptyItemViewModel.OpenSourceEmptyItemViewModelListener {

        override fun onBind(position: Int) {
            val emptyItemViewModel = OpenSourceEmptyItemViewModel(this)
            mBinding.viewModel = emptyItemViewModel
        }

        override fun onRetryClick() {
            mListener!!.onRetryClick()
        }
    }

    inner class OpenSourceViewHolder(private val mBinding: ItemOpenSourceViewBinding) :
        BaseViewHolder(mBinding.root), View.OnClickListener {

        override fun onBind(position: Int) {
            val mOpenSourceItemViewModel = mOpenSourceResponseList[position]
            mBinding.viewModel = mOpenSourceItemViewModel

            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.
            mBinding.executePendingBindings()
        }

        override fun onClick(view: View) {
            if (mOpenSourceResponseList[0].projectUrl.get() != null) {
                try {
                    val intent = Intent()
                    intent.action = Intent.ACTION_VIEW
                    intent.addCategory(Intent.CATEGORY_BROWSABLE)
                    intent.data = Uri.parse(mOpenSourceResponseList[0].projectUrl.get())
                    itemView.context.startActivity(intent)
                } catch (e: Exception) {
                    AppLogger.d("url error")
                }

            }
        }
    }

    companion object {

        val VIEW_TYPE_EMPTY = 0

        val VIEW_TYPE_NORMAL = 1
    }
}