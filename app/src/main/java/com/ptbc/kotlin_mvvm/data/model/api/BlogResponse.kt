package com.ptbc.kotlin_mvvm.data.model.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class BlogResponse {

    @Expose
    @SerializedName("data")
    val data: List<Blog>? = null

    @Expose
    @SerializedName("message")
    val message: String? = null

    @Expose
    @SerializedName("status_code")
    val statusCode: String? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other !is BlogResponse) {
            return false
        }

        val that = other as BlogResponse?

        if (statusCode != that!!.statusCode) {
            return false
        }
        return if (message != that.message) {
            false
        } else data == that.data

    }

    override fun hashCode(): Int {
        var result = statusCode!!.hashCode()
        result = 31 * result + message!!.hashCode()
        result = 31 * result + data!!.hashCode()
        return result
    }

    class Blog {

        @Expose
        @SerializedName("author")
        val author: String? = null

        @Expose
        @SerializedName("blog_url")
        val blogUrl: String? = null

        @Expose
        @SerializedName("img_url")
        val coverImgUrl: String? = null

        @Expose
        @SerializedName("published_at")
        val date: String? = null

        @Expose
        @SerializedName("description")
        val description: String? = null

        @Expose
        @SerializedName("title")
        val title: String? = null

        override fun equals(other: Any?): Boolean {
            if (this === other) {
                return true
            }
            if (other !is Blog) {
                return false
            }

            val blog = other as Blog?

            if (blogUrl != blog!!.blogUrl) {
                return false
            }
            if (coverImgUrl != blog.coverImgUrl) {
                return false
            }
            if (title != blog.title) {
                return false
            }
            if (description != blog.description) {
                return false
            }
            return if (author != blog.author) {
                false
            } else date == blog.date

        }

        override fun hashCode(): Int {
            var result = blogUrl!!.hashCode()
            result = 31 * result + coverImgUrl!!.hashCode()
            result = 31 * result + title!!.hashCode()
            result = 31 * result + description!!.hashCode()
            result = 31 * result + author!!.hashCode()
            result = 31 * result + date!!.hashCode()
            return result
        }
    }
}