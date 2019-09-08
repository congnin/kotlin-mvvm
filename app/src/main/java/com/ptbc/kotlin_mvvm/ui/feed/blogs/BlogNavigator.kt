package com.ptbc.kotlin_mvvm.ui.feed.blogs

import com.ptbc.kotlin_mvvm.data.model.api.BlogResponse


interface BlogNavigator {

    fun handleError(throwable: Throwable)

    fun updateBlog(blogList: List<BlogResponse.Blog>?)
}