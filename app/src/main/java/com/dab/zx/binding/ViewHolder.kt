package com.dab.zx.binding

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView

/**
 * Created by 八神火焰 on 2018/1/3.
 */
open class ViewHolder(var binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)

class SubitemViewHolder(binding: ViewDataBinding) : ViewHolder(binding)

class HeaderViewHolder(binding: ViewDataBinding) : ViewHolder(binding)