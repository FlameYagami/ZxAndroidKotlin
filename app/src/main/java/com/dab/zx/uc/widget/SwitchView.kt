package com.dab.zx.uc.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.dab.zx.R
import com.dab.zx.uitls.SpUtils
import kotlinx.android.synthetic.main.widget_switch.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Created by 八神火焰 on 2017/3/15.
 */

class SwitchView(context: Context, attrs: AttributeSet?) : LinearLayout(context, attrs) {

    init {

        val view = View.inflate(context, R.layout.widget_switch, null)
        addView(view)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SwitchView)
        val titleText = typedArray.getString(R.styleable.SwitchView_title_text)
        tv_key.text = titleText
        typedArray.recycle()

        switch_compat.onClick { onSwitchCompat_Click() }
    }

    val key: String
        get() = tv_key.text.toString().trim { it <= ' ' }

    fun setChecked(isChecked: Boolean) {
        switch_compat.isChecked = isChecked
        SpUtils.putInt(key, if (switch_compat.isChecked) 1 else 0)
    }

    fun onSwitchCompat_Click() {
        setChecked(switch_compat.isChecked)
    }
}
