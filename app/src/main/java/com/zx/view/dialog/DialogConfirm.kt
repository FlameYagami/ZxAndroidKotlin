package com.zx.view.dialog

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.DialogInterface.OnClickListener
import android.os.Handler
import android.os.Looper
import android.os.Message

class DialogConfirm private constructor(context: Context, title: String) {
    var context: Context? = null
    var result: Int = 0
        private set

    init {
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setPositiveButton("确定", DialogButtonOnClick(1))
        dialogBuilder.setNegativeButton("取消", DialogButtonOnClick(0))
        dialogBuilder.setOnCancelListener(DialogCancelOnClick())
        dialogBuilder.setTitle(title).create().show()
        try {
            Looper.loop()
        } catch (ignored: Exception) {
        }

    }

    private class MyHandler : Handler() {
        override fun handleMessage(message: Message) {
            throw RuntimeException()
        }
    }

    /**
     * 确定、取消按钮的监听
     */
    private inner class DialogButtonOnClick internal constructor(internal var type: Int) : OnClickListener {

        override fun onClick(dialog: DialogInterface, which: Int) {
            this@DialogConfirm.result = type
            val m = handler?.obtainMessage()
            handler?.sendMessage(m)
        }
    }

    /**
     * 返回键的监听
     */
    private inner class DialogCancelOnClick : DialogInterface.OnCancelListener {
        override fun onCancel(dialog: DialogInterface) {
            this@DialogConfirm.result = 0
            val m = handler?.obtainMessage()
            handler?.sendMessage(m)
        }
    }

    companion object {
        private var handler: Handler? = null

        fun show(context: Context, title: String): Boolean {
            handler = MyHandler()
            return DialogConfirm(context, title).result == 1
        }
    }
}
