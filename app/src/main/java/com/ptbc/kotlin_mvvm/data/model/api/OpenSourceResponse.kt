package com.ptbc.kotlin_mvvm.data.model.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class OpenSourceResponse {

    @Expose
    @SerializedName("data")
    var data: List<Repo>? = null

    @Expose
    @SerializedName("message")
    var message: String? = null

    @Expose
    @SerializedName("status_code")
    var statusCode: String? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other !is OpenSourceResponse) {
            return false
        }

        val that = other as OpenSourceResponse?

        if (statusCode != that!!.statusCode) {
            return false
        }
        if (message != that.message) {
            return false
        }
        return if (data != null) data == that.data else that.data == null

    }

    override fun hashCode(): Int {
        var result = statusCode!!.hashCode()
        result = 31 * result + message!!.hashCode()
        result = 31 * result + if (data != null) data!!.hashCode() else 0
        return result
    }

    class Repo {

        @Expose
        @SerializedName("img_url")
        val coverImgUrl: String? = null

        @Expose
        @SerializedName("description")
        val description: String? = null

        @Expose
        @SerializedName("project_url")
        val projectUrl: String? = null

        @Expose
        @SerializedName("title")
        val title: String? = null

        override fun equals(other: Any?): Boolean {
            if (this === other) {
                return true
            }
            if (other !is Repo) {
                return false
            }

            val repo = other as Repo?

            if (projectUrl != repo!!.projectUrl) {
                return false
            }
            if (coverImgUrl != repo.coverImgUrl) {
                return false
            }
            return if (title != repo.title) {
                false
            } else description == repo.description

        }

        override fun hashCode(): Int {
            var result = projectUrl!!.hashCode()
            result = 31 * result + coverImgUrl!!.hashCode()
            result = 31 * result + title!!.hashCode()
            result = 31 * result + description!!.hashCode()
            return result
        }
    }
}