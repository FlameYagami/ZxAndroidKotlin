package com.dab.zx.binding

import android.content.Context
import android.databinding.ViewDataBinding

/**
 * Created by 八神火焰 on 2018/1/3.
 */
open class SingleItemVm<SubitemView : ViewDataBinding, T> protected constructor(protected var context: Context) : DataVm<T>() {
    open var adapter: SingleItemAdapter<SubitemView, T> = SingleItemAdapter(context)
}