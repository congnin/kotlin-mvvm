package com.ptbc.kotlin_mvvm.ui.feed.blogs

class BlogEmptyItemViewModel(private val mListener: BlogEmptyItemViewModelListener) {

    fun onRetryClick() {
        mListener.onRetryClick()
    }

    interface BlogEmptyItemViewModelListener {

        fun onRetryClick()
    }
}