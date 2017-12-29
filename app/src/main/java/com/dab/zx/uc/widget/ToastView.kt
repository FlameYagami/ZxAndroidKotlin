package com.dab.zx.uc.widget

import android.content.Context
import android.view.View
import android.widget.Toast
import com.dab.zx.R
import kotlinx.android.synthetic.main.widget_toast.view.*

/**
 * Created by 八神火焰 on 2017/3/1.
 */

class ToastView private constructor(context: Context, text: CharSequence, duration: Int) {

    private val mToast: Toast?

    init {
        val view = View.inflate(context, R.layout.widget_toast, null)
        view.tv_value.text = text
        mToast = Toast(context)
        mToast.duration = duration
        mToast.view = view
    }

    fun show() {
        mToast?.show()
    }

    fun setGravity(gravity: Int, xOffset: Int, yOffset: Int) {
        mToast?.setGravity(gravity, xOffset, yOffset)
    }

    companion object {

        fun make(context: Context, text: CharSequence, duration: Int): ToastView {
            return ToastView(context, text, duration)
        }
    }
}
