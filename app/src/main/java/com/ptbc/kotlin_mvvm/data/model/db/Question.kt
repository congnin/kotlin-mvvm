package com.ptbc.kotlin_mvvm.data.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "questions")
class Question {

    @Expose
    @SerializedName("created_at")
    @ColumnInfo(name = "created_at")
    var createdAt: String? = null

    @Expose
    @PrimaryKey
    var id: Long? = null

    @Expose
    @SerializedName("question_img_url")
    @ColumnInfo(name = "question_img_url")
    var imgUrl: String? = null

    @Expose
    @SerializedName("question_text")
    @ColumnInfo(name = "question_text")
    var questionText: String? = null

    @Expose
    @SerializedName("updated_at")
    @ColumnInfo(name = "updated_at")
    var updatedAt: String? = null
}