package com.ptbc.kotlin_mvvm.ui.main

import android.graphics.Color
import android.widget.Button
import android.widget.TextView
import com.androidnetworking.widget.ANImageView
import com.mindorks.placeholderview.annotations.*
import com.ptbc.kotlin_mvvm.R
import com.ptbc.kotlin_mvvm.data.model.other.QuestionCardData

@NonReusable
@Layout(R.layout.card_layout)
class QuestionCard(private val mQuestionCardData: QuestionCardData) {

    @View(R.id.btn_option_1)
    private val mOption1Button: Button? = null

    @View(R.id.btn_option_2)
    private val mOption2Button: Button? = null

    @View(R.id.btn_option_3)
    private val mOption3Button: Button? = null

    @View(R.id.iv_pic)
    private val mPicImageView: ANImageView? = null

    @View(R.id.tv_question_txt)
    private val mQuestionTextView: TextView? = null

    @Click(R.id.btn_option_1)
    fun onOption1Click() {
        showCorrectOptions()
    }

    @Click(R.id.btn_option_2)
    fun onOption2Click() {
        showCorrectOptions()
    }

    @Click(R.id.btn_option_3)
    fun onOption3Click() {
        showCorrectOptions()
    }

    @Resolve
    private fun onResolved() {
        mQuestionTextView!!.setText(mQuestionCardData.question.questionText)
        if (mQuestionCardData.mShowCorrectOptions) {
            showCorrectOptions()
        }

        for (i in 0..2) {
            var button: Button? = null
            when (i) {
                0 -> button = mOption1Button
                1 -> button = mOption2Button
                2 -> button = mOption3Button
            }

            button?.setText(mQuestionCardData.options.get(i).optionText)

            if (mQuestionCardData.question.imgUrl != null) {
                mPicImageView!!.setImageUrl(mQuestionCardData.question.imgUrl)
            }
        }
    }

    private fun showCorrectOptions() {
        mQuestionCardData.mShowCorrectOptions = true
        for (i in 0..2) {
            val option = mQuestionCardData.options.get(i)
            var button: Button? = null
            when (i) {
                0 -> button = mOption1Button
                1 -> button = mOption2Button
                2 -> button = mOption3Button
            }
            if (button != null) {
                if (option.isCorrect) {
                    button.setBackgroundColor(Color.GREEN)
                } else {
                    button.setBackgroundColor(Color.RED)
                }
            }
        }
    }
}