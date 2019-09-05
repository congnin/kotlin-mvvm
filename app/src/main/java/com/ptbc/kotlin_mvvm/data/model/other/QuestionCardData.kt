package com.ptbc.kotlin_mvvm.data.model.other

import com.ptbc.kotlin_mvvm.data.model.db.Option
import com.ptbc.kotlin_mvvm.data.model.db.Question

class QuestionCardData(var question: Question, var options: List<Option>) {

    var mShowCorrectOptions: Boolean = false
}