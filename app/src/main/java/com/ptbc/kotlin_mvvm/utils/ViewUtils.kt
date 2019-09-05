package com.ptbc.kotlin_mvvm.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.ptbc.kotlin_mvvm.R
import kotlin.math.roundToInt

object ViewUtils {

    fun changeIconDrawableToGray(context: Context, drawable: Drawable?) {
        if (drawable != null) {
            drawable.mutate()
            drawable.setColorFilter(
                ContextCompat.getColor(context, R.color.dark_gray),
                PorterDuff.Mode.SRC_ATOP
            )
        }
    }

    fun dpToPx(dp: Float): Int {
        val density = Resources.getSystem().displayMetrics.density
        return (dp * density).roundToInt()
    }

    fun pxToDp(px: Float): Float {
        val densityDpi = Resources.getSystem().displayMetrics.densityDpi.toFloat()
        return px / (densityDpi / 160f)
    }
}// This class is not publicly instantiable