package com.zx.uitls

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import com.zx.R
import kotlinx.android.synthetic.main.popupwindow_hand.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick

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
            view.popup_set.onClick { Set_Click() }
            view.popup_launch.onClick { Launch_Click() }
        }

        fun Set_Click() {
            dismiss()
        }

        fun Launch_Click() {
            dismiss()
        }
    }
}
