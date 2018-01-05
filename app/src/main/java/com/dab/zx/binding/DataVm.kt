package com.dab.zx.binding

import android.databinding.ObservableArrayList
import android.view.View

/**
 * Created by 八神火焰 on 2018/1/3.
 */
open class DataVm<T> {
    open var data: ObservableArrayList<T> = ObservableArrayList()

    var itemClickListener: ((view: View, data: List<T>, position: Int) -> Unit)? = null
    var itemLongClickListener: ((view: View, data: List<T>, position: Int) -> Unit)? = null

    fun setOnItemClickListener(mListener: (view: View, data: List<T>, position: Int) -> Unit) {
        itemClickListener = mListener
    }

    fun setOnItemLongClickListener(mListener: (view: View, data: List<T>, position: Int) -> Unit) {
        itemLongClickListener = mListener
    }
}