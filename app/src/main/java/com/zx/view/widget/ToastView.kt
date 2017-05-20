package com.zx.view.widget

import android.content.Context
import android.view.View
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.zx.R

/**
 * Created by 八神火焰 on 2017/3/1.
 */

class ToastView private constructor(context: Context, text: CharSequence, duration: Int) {
    @BindView(R.id.textView)
    internal var textView: TextView? = null

    private val mToast: Toast?

    init {
        val view = View.inflate(context, R.layout.weight_toast, null)
        ButterKnife.bind(this, view)
        textView?.text = text
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
