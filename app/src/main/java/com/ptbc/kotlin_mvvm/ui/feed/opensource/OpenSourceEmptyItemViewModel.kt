package com.ptbc.kotlin_mvvm.ui.feed.opensource

class OpenSourceEmptyItemViewModel(private val mListener: OpenSourceEmptyItemViewModelListener) {

    fun onRetryClick() {
        mListener.onRetryClick()
    }

    interface OpenSourceEmptyItemViewModelListener {

        fun onRetryClick()
    }
}