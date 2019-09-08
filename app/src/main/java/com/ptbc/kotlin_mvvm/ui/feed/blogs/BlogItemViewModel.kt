package com.ptbc.kotlin_mvvm.ui.feed.blogs

import androidx.databinding.ObservableField
import com.ptbc.kotlin_mvvm.data.model.api.BlogResponse


class BlogItemViewModel(
    private val mBlog: BlogResponse.Blog,
    val mListener: BlogItemViewModelListener
) {

    val author: ObservableField<String>

    val content: ObservableField<String>

    val date: ObservableField<String>

    val imageUrl: ObservableField<String>

    val title: ObservableField<String>

    init {
        imageUrl = ObservableField<String>(mBlog.coverImgUrl)
        title = ObservableField<String>(mBlog.title)
        author = ObservableField<String>(mBlog.author)
        date = ObservableField<String>(mBlog.date)
        content = ObservableField<String>(mBlog.description)
    }

    fun onItemClick() {
        mListener.onItemClick(mBlog.blogUrl)
    }

    interface BlogItemViewModelListener {

        fun onItemClick(blogUrl: String?)
    }
}