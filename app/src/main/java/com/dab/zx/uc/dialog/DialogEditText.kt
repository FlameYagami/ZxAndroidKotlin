package com.dab.zx.uc.dialog

import android.content.Context
import android.support.v7.app.AlertDialog
import android.text.TextUtils
import android.view.View
import com.dab.zx.R
import kotlinx.android.synthetic.main.dialog_edittext.*
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Created by 八神火焰 on 2016/12/22.
 */

class DialogEditText(context: Context, title: String, content: String, hint: String,
                     internal var mButtonClickListener: (dialog: DialogEditText, content: String) -> Unit) : AlertDialog(context) {

    init {
        val view = View.inflate(context, R.layout.dialog_edittext, null)
        setView(view)
        setTitle(title)
        editText.hint = hint
        editText.setText(content)
        editText.setSelection(editText.text.length)
        btnCancel.onClick { dismiss() }
        btnOk.onClick {
            val text = editText.text.toString().trim()
            if (!TextUtils.isEmpty(text)) {
                mButtonClickListener.invoke(this@DialogEditText, text)
            }
        }
    }
}
