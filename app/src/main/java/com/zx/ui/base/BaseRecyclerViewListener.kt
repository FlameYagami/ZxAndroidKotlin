package com.zx.ui.base

import android.view.MotionEvent
import android.view.View

/**
 * Created by 八神火焰 on 2016/12/22.
 */

interface BaseRecyclerViewListener {
    interface OnItemClickListener {
        fun onItemClick(view: View, data: List<*>, position: Int)
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(view: View, data: List<*>, position: Int)
    }

    interface OnItemTouchListener {
        fun OnItemTouch(view: View, motionEvent: MotionEvent, data: List<*>, position: Int)
    }
}
