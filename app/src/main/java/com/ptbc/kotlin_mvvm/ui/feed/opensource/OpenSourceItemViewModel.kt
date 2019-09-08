package com.ptbc.kotlin_mvvm.ui.feed.opensource

import androidx.databinding.ObservableField


class OpenSourceItemViewModel(
    imageUrl: String?,
    title: String?,
    content: String?,
    projectUrl: String?
) {

    val content = ObservableField<String>()

    val imageUrl = ObservableField<String>()

    val projectUrl = ObservableField<String>()

    val title = ObservableField<String>()

    init {
        this.imageUrl.set(imageUrl)
        this.title.set(title)
        this.content.set(content)
        this.projectUrl.set(projectUrl)
    }
}