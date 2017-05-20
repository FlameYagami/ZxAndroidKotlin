package com.zx.view.widget

import android.content.Context
import android.support.v7.widget.SwitchCompat
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.zx.R
import com.zx.uitls.SpUtil

/**
 * Created by 八神火焰 on 2017/3/15.
 */

class SwitchView(context: Context, attrs: AttributeSet?) : LinearLayout(context, attrs) {
    @BindView(R.id.switch_compat)
    internal var switchCompat: SwitchCompat? = null
    @BindView(R.id.tv_key)
    internal var textView: TextView? = null

    init {

        val view = View.inflate(context, R.layout.widget_switch, null)
        ButterKnife.bind(this, view)
        addView(view)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SwitchView)
        val titleText = typedArray.getString(R.styleable.SwitchView_title_text)
        textView?.text = titleText
        typedArray.recycle()
    }

    val key: String
        get() = textView?.text.toString().trim { it <= ' ' }

    fun setChecked(isChecked: Boolean) {
        switchCompat?.isChecked = isChecked
        SpUtil.instances.putInt(key, if (switchCompat?.isChecked as Boolean) 1 else 0)
    }

    @OnClick(R.id.switch_compat)
    fun onSwitchCompat_Click() {
        setChecked(switchCompat?.isChecked as Boolean)
    }
}
