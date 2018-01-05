package com.dab.zx.binding

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ObservableArrayList
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * Created by 八神火焰 on 2018/1/3.
 */
open class SingleItemAdapter<in SubitemView : ViewDataBinding, T>(protected var context: Context) : RecyclerView.Adapter<ViewHolder>() {

    private var data: ObservableArrayList<T> = ObservableArrayList()
    private lateinit var subitemView: ItemView
    private lateinit var dataVm: DataVm<T>

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            setVariable(subitemView.bindingVariable(), data[position])
            executePendingBindings()
        }
        setItemClickListener(holder)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return SubitemViewHolder(DataBindingUtil.inflate<SubitemView>(LayoutInflater.from(context), subitemView.layoutRes(), parent, false))
    }

    open fun lateInit(data: List<T>, dataVm: DataVm<T>, subitemView: ItemView) {
        this.dataVm = dataVm
        this.subitemView = subitemView
        updateData(data)
    }

    private fun setItemClickListener(holder: ViewHolder) {
        holder.binding.root.setOnClickListener {
            dataVm.itemClickListener?.invoke(holder.binding.root, data, holder.adapterPosition)
        }
        holder.binding.root.setOnLongClickListener {
            dataVm.itemLongClickListener?.invoke(holder.binding.root, data, holder.adapterPosition)
            false
        }
    }

    fun updateData(data: List<T>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    fun addData(data: List<T>, position: Int) {
        this.data.clear()
        this.data.addAll(data)
        notifyItemInserted(position)
    }

    fun removeData(data: List<T>, position: Int) {
        this.data.clear()
        this.data.addAll(data)
        notifyItemRemoved(position)
    }
}