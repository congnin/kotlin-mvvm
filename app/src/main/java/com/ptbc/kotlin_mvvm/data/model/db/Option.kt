package com.ptbc.kotlin_mvvm.data.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "options",
    foreignKeys = [ForeignKey(
        entity = Question::class,
        parentColumns = ["id"],
        childColumns = ["question_id"]
    )]
)
class Option {

    @Expose
    @SerializedName("created_at")
    @ColumnInfo(name = "created_at")
    var createdAt: String? = null

    @Expose
    @PrimaryKey
    var id: Long? = null

    @Expose
    @SerializedName("is_correct")
    @ColumnInfo(name = "is_correct")
    var isCorrect: Boolean = false

    @Expose
    @SerializedName("option_text")
    @ColumnInfo(name = "option_text")
    var optionText: String? = null

    @Expose
    @SerializedName("question_id")
    @ColumnInfo(name = "question_id")
    var questionId: Long? = null

    @Expose
    @SerializedName("updated_at")
    @ColumnInfo(name = "updated_at")
    var updatedAt: String? = null
}