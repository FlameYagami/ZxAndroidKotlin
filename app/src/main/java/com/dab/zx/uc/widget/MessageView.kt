package com.dab.zx.uc.widget

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.dab.zx.R
import com.dab.zx.uitls.SpUtils
import kotlinx.android.synthetic.main.widget_message.view.*

/**
 * Created by 八神火焰 on 2017/1/12.
 */
class MessageView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    init {
        val view = View.inflate(context, R.layout.widget_message, null)
        addView(view)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.MessageView)
        val keyText = typedArray.getString(R.styleable.MessageView_title_text)
        val valueText = typedArray.getString(R.styleable.MessageView_message_text)
        typedArray.recycle()
        tv_key.text = keyText
        if (TextUtils.isEmpty(valueText)) {
            tv_value.visibility = View.GONE
        } else {
            tv_value.text = valueText
        }
        view.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    fun setDefaultSp(key: String, value: String) {
        SpUtils.putString(key, value)
        tv_value.text = value
    }

    fun setValue(valueText: String) {
        tv_value.visibility = if (TextUtils.isEmpty(valueText)) View.GONE else View.VISIBLE
        tv_value.text = valueText
    }
}
