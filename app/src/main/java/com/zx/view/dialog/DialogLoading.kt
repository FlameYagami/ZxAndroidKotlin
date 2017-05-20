package com.zx.view.dialog

import android.app.ProgressDialog
import android.content.Context
import android.text.TextUtils

/**
 * Created by 八神火焰 on 2016/12/1.
 */

object DialogLoading {
    private var dialog: ProgressDialog? = null

    fun showDialog(context: Context, msg: String, cancelable: Boolean) {
        dialog = ProgressDialog.show(context, null, if (!TextUtils.isEmpty(msg)) msg else "请稍后...", true, cancelable)
    }

    fun hideDialog() {
        if (null != dialog && dialog?.isShowing as Boolean) {
            dialog?.dismiss()
            dialog = null
        }
    }
}
