package com.zx.view.dialog

import android.content.Context
import android.support.v7.app.AlertDialog
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.zx.R

/**
 * Created by 八神火焰 on 2016/12/22.
 */

class DialogEditText(context: Context, title: String, content: String, hint: String, private val onButtonClick: DialogEditText.OnButtonClick) : AlertDialog(context) {
    @BindView(R.id.editText)
    internal var editText: EditText? = null
    @BindView(R.id.tv_cancel)
    internal var tvCancel: TextView? = null
    @BindView(R.id.tv_ok)
    internal var tvOk: TextView? = null

    interface OnButtonClick {
        fun getText(dialog: DialogEditText, content: String)
    }

    init {
        val view = View.inflate(context, R.layout.dialog_edittext, null)
        ButterKnife.bind(this, view)

        setView(view)
        setTitle(title)
        editText?.hint = hint
        editText?.setText(content)
        editText?.setSelection(editText?.text?.length as Int)
        tvCancel?.setOnClickListener { dismiss() }
        tvOk?.setOnClickListener {
            val text = editText?.text.toString().trim { it <= ' ' }
            if (!TextUtils.isEmpty(text)) {
                this.onButtonClick.getText(this, text)
            }
        }
    }
}
