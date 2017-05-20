package com.zx.uitls

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import butterknife.ButterKnife
import butterknife.OnClick
import com.zx.R

/**
 * Created by 八神火焰 on 2017/1/14.
 */

class MyPopup(context: Context, layoutId: Int) : PopupWindow() {
    var mHandViewHolder: HandViewHolder? = null

    init {
        val view = View.inflate(context, layoutId, null)
        contentView = view
        width = ViewGroup.LayoutParams.WRAP_CONTENT
        height = ViewGroup.LayoutParams.WRAP_CONTENT
        isOutsideTouchable = true
        when (layoutId) {
            R.layout.popupwindow_hand -> {
                mHandViewHolder = HandViewHolder(view)
            }
        }
    }

    inner class HandViewHolder internal constructor(view: View) {
        init {
            ButterKnife.bind(this, view)
        }

        @OnClick(R.id.popup_set)
        fun Set_Click() {
            dismiss()
        }

        @OnClick(R.id.popup_launch)
        fun Launch_Click() {
            dismiss()
        }
    }
}
