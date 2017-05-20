package com.zx.view.widget

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.zx.R
import com.zx.uitls.SpUtil

/**
 * Created by 八神火焰 on 2017/1/12.
 */
class MessageView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {
    @BindView(R.id.tv_key)
    internal var tvKey: TextView? = null
    @BindView(R.id.tv_value)
    internal var tvValue: TextView? = null

    init {

        val view = View.inflate(context, R.layout.widget_message, null)
        addView(view)
        ButterKnife.bind(this, view)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.MessageView)
        val keyText = typedArray.getString(R.styleable.MessageView_title_text)
        val valueText = typedArray.getString(R.styleable.MessageView_message_text)
        typedArray.recycle()
        tvKey?.text = keyText
        if (TextUtils.isEmpty(valueText)) {
            tvValue?.visibility = View.GONE
        } else {
            tvValue?.text = valueText
        }
        view.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    fun setDefaultSp(key: String, value: String) {
        SpUtil.instances.putString(key, value)
        tvValue?.text = value
    }

    fun setValue(valueText: String) {
        tvValue?.visibility = if (TextUtils.isEmpty(valueText)) View.GONE else View.VISIBLE
        tvValue?.text = valueText
    }
}
