package com.ptbc.kotlin_mvvm.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mindorks.placeholderview.SwipePlaceHolderView
import com.ptbc.kotlin_mvvm.data.model.other.QuestionCardData
import com.ptbc.kotlin_mvvm.ui.feed.opensource.OpenSourceAdapter
import com.ptbc.kotlin_mvvm.ui.feed.opensource.OpenSourceItemViewModel
import com.ptbc.kotlin_mvvm.ui.main.MainViewModel
import com.ptbc.kotlin_mvvm.ui.main.QuestionCard

object BindingUtils {

//    @BindingAdapter("adapter")
//    fun addBlogItems(recyclerView: RecyclerView, blogs: List<BlogResponse.Blog>) {
//        val adapter = recyclerView.adapter as BlogAdapter?
//        if (adapter != null) {
//            adapter!!.clearItems()
//            adapter!!.addItems(blogs)
//        }
//    }

    @BindingAdapter("adapter")
    @JvmStatic
    fun addOpenSourceItems(
        recyclerView: RecyclerView,
        openSourceItems: List<OpenSourceItemViewModel>?
    ) {
        val adapter = recyclerView.adapter as OpenSourceAdapter?
        if (adapter != null && openSourceItems != null) {
            adapter.clearItems()
            adapter.addItems(openSourceItems)
        }
    }

    @BindingAdapter("adapter", "action")
    @JvmStatic
    fun addQuestionItems(
        mCardsContainerView: SwipePlaceHolderView,
        mQuestionList: List<QuestionCardData>?,
        mAction: Int
    ) {
        if (mAction == MainViewModel.ACTION_ADD_ALL) {
            if (mQuestionList != null) {
                mCardsContainerView.removeAllViews()
                for (question in mQuestionList) {
                    if (question.options.size == 3) {
                        mCardsContainerView.addView<Any>(QuestionCard(question))
                    }
                }
                ViewAnimationUtils.scaleAnimateView(mCardsContainerView)
            }
        }
    }

    @BindingAdapter("imageUrl")
    @JvmStatic
    fun setImageUrl(imageView: ImageView, url: String) {
        val context = imageView.context
        Glide.with(context).load(url).into(imageView)
    }
}// This class is not publicly instantiable