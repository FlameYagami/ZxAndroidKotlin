package com.zx.view.dialog

import android.content.Context
import android.support.v7.app.AlertDialog
import android.text.TextUtils
import android.view.View
import com.zx.R
import kotlinx.android.synthetic.main.dialog_edittext.*

/**
 * Created by 八神火焰 on 2016/12/22.
 */

class DialogEditText(context: Context, title: String, content: String, hint: String, mButtonClickListener: (dialog: DialogEditText, content: String) -> Unit) : AlertDialog(context) {

    var mButtonClickListener: ((dialog: DialogEditText, content: String) -> Unit)? = null

    init {
        val view = View.inflate(context, R.layout.dialog_edittext, null)
        this.mButtonClickListener = mButtonClickListener
        setView(view)
        setTitle(title)
        editText.hint = hint
        editText.setText(content)
        editText.setSelection(editText.text.length)
        btnCancel.setOnClickListener { dismiss() }
        btnOk.setOnClickListener {
            val text = editText.text.toString().trim { it <= ' ' }
            if (!TextUtils.isEmpty(text)) {
                mButtonClickListener.invoke(this, text)
            }
        }
    }
}
