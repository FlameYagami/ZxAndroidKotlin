package com.dab.zx.binding

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ObservableArrayList
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.dab.zx.model.MultiItemModel

/**
 * Created by 八神火焰 on 2018/1/3.
 */
open class MultiItemAdapter<HeaderView : ViewDataBinding, SubitemView : ViewDataBinding, R, T>
(protected var context: Context) : RecyclerView.Adapter<ViewHolder>() {

    protected var data: ObservableArrayList<Any> = ObservableArrayList()
    private var multiDatas: ArrayList<MultiItemModel<R, T>> = arrayListOf()

    private var typeHeader = 1
    private var typeSubitem = 0

    private lateinit var subitemView: ItemView
    private lateinit var headerView: ItemView
    private lateinit var dataVm: DataVm<T>

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (data is MultiItemModel.Subitem<*>) {
            holder.binding.setVariable(subitemView.layoutRes(), data[position])
        } else {
            holder.binding.setVariable(headerView.layoutRes(), data[position])
        }
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        val data = this.data[position]
        return if (data is MultiItemModel.Subitem<*>)
            typeSubitem
        else
            typeHeader
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            typeSubitem -> SubitemViewHolder(DataBindingUtil.inflate<SubitemView>(LayoutInflater.from(context), subitemView.layoutRes(), parent, false))
            typeHeader -> HeaderViewHolder(DataBindingUtil.inflate<HeaderView>(LayoutInflater.from(context), headerView.layoutRes(), parent, false))
            else -> throw IllegalAccessException("Maybe you use error item type:" + viewType)
        }
    }

    open fun lateInit(data: List<T>, dataVm: DataVm<T>, headerView: ItemView, subitemView: ItemView) {
        this.dataVm = dataVm
        this.headerView = headerView
        this.subitemView = subitemView
        updateDataExList()
    }


    fun updateData(data: List<Any>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    fun updateDataExList() {
        val tempDataList = ArrayList<Any>()
        multiDatas.map { }
        for (multiData in multiDatas) {
            tempDataList.add(multiData.getHeader())
            if (multiData.getHeader().isExpanded) {
                tempDataList.addAll(multiData.getSubitems())
            }
        }
        updateData(tempDataList)
    }
}