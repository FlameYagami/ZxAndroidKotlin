package com.zx.ui.base

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.util.*

/**
 * Created by 八神火焰 on 2016/12/22.
 */

abstract class BaseRecyclerViewAdapter protected constructor(protected var context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    protected abstract val layoutId: Int

    protected abstract fun getViewHolder(view: View): RecyclerView.ViewHolder

    protected abstract fun getView(holder: RecyclerView.ViewHolder, position: Int)

    protected var data: List<*> = ArrayList<Any>()

    var itemClickListener: ((view: View, data: List<*>, position: Int) -> Unit)? = null
    var itemLongClickListener: ((view: View, data: List<*>, position: Int) -> Unit)? = null

    fun setOnItemClickListener(mListener: (view: View, data: List<*>, position: Int) -> Unit) {
        this.itemClickListener = mListener
    }

    fun setOnItemLongClickListener(mListener: (view: View, data: List<*>, position: Int) -> Unit) {
        this.itemLongClickListener = mListener
    }

    fun updateData(data: List<*>) {
        this.data = data
        notifyDataSetChanged()
    }

    fun addData(data: List<*>, position: Int) {
        this.data = data
        notifyItemInserted(position)
    }

    fun removeData(data: List<*>, position: Int) {
        this.data = data
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(layoutId, parent, false)
        return getViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getView(holder, position)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}
