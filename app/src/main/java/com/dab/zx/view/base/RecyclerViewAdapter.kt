package com.dab.zx.view.base

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ObservableArrayList
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * @param B the ViewDataBinding of adapter
 * @param M the model of adapter
 */
abstract class RecyclerViewAdapter<in B : ViewDataBinding, M> protected constructor(protected var context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    protected abstract val layoutId: Int

    protected abstract fun getViewHolder(viewDataBinding: B, viewType: Int): RecyclerView.ViewHolder

    protected abstract fun getView(holder: RecyclerView.ViewHolder, position: Int)

    protected var data: ObservableArrayList<M> = ObservableArrayList()

    var itemClickListener: ((view: View, data: List<*>, position: Int) -> Unit)? = null
    var itemLongClickListener: ((view: View, data: List<*>, position: Int) -> Unit)? = null

    fun setOnItemClickListener(mListener: (view: View, data: List<*>, position: Int) -> Unit) {
        this.itemClickListener = mListener
    }

    fun setOnItemLongClickListener(mListener: (view: View, data: List<*>, position: Int) -> Unit) {
        this.itemLongClickListener = mListener
    }

    fun updateData(data: List<M>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    fun addData(data: List<M>, position: Int) {
        this.data.clear()
        this.data.addAll(data)
        notifyItemInserted(position)
    }

    fun removeData(data: List<M>, position: Int) {
        this.data.clear()
        this.data.addAll(data)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = DataBindingUtil.inflate<B>(LayoutInflater.from(context), layoutId, parent, false)
        return getViewHolder(binding, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getView(holder, position)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}
