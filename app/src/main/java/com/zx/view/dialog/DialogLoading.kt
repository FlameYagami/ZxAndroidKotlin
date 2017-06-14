package com.zx.view.dialog

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.text.TextUtils
import android.view.View
import com.zx.R
import kotlinx.android.synthetic.main.dialog_loading.*

/**
 * Created by 八神火焰 on 2016/12/1.
 */

class DialogLoading : AlertDialog {
    protected constructor(context: Context) : super(context) {
        initView(context, null, null)
    }

    protected constructor(context: Context, message: CharSequence) : super(context) {
        initView(context, message, null)
    }

    protected constructor(context: Context, onKeyListener: DialogInterface.OnKeyListener) : super(context) {
        initView(context, null, onKeyListener)
    }

    protected constructor(context: Context, message: CharSequence, onKeyListener: DialogInterface.OnKeyListener) : super(context) {
        initView(context, message, onKeyListener)
    }

    private fun initView(context: Context, message: CharSequence?, onKeyListener: DialogInterface.OnKeyListener?) {
        val view = View.inflate(context, R.layout.dialog_loading, null)
        setView(view)
        var waiting = context.getString(R.string.waiting)
        tvMessage.text = if (TextUtils.isEmpty(message)) waiting else message
        setCanceledOnTouchOutside(false)
        if (onKeyListener != null) {
            setCancelable(true)
            setOnKeyListener(onKeyListener)
        } else {
            setCancelable(false)
            setOnKeyListener(null)
        }
    }
}
