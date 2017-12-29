package com.dab.zx.uc.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.dab.zx.R
import kotlinx.android.synthetic.main.widget_select.view.*

/**
 * Created by 八神火焰 on 2017/1/12.
 */
class SelectView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    init {

        val view = View.inflate(context, R.layout.widget_select, null)
        addView(view)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SelectView)
        val keyText = typedArray.getString(R.styleable.SelectView_title_text)
        val valueText = typedArray.getString(R.styleable.SelectView_message_text)
        val isEnable = typedArray.getBoolean(R.styleable.SelectView_enable, true)
        typedArray.recycle()
        tv_key!!.text = keyText
        tv_value!!.text = valueText
        if (!isEnable) {
            img_right_arrow!!.visibility = View.GONE
        }
        view.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    var value: String
        get() = tv_value!!.text.toString().trim { it <= ' ' }
        set(message) {
            tv_value!!.text = message
        }
}
