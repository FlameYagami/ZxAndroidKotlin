package com.dab.zx.view.search


import android.content.Context
import android.support.v7.widget.RecyclerView
import com.dab.zx.R
import com.dab.zx.bean.CardBean
import com.dab.zx.databinding.ItemCardPreviewBinding
import com.dab.zx.view.base.RecyclerViewAdapter


class CardPreviewAdapter(context: Context) : RecyclerViewAdapter<ItemCardPreviewBinding, CardBean>(context) {

    override val layoutId: Int
        get() = R.layout.item_card_preview

    override fun getViewHolder(viewDataBinding: ItemCardPreviewBinding, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(viewDataBinding)
    }

    override fun getView(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as ViewHolder
        viewHolder.binding.item = data[position]
        viewHolder.binding.executePendingBindings()
        viewHolder.binding.viewItemContent.setOnClickListener { itemClickListener?.invoke(it, data, position) }
    }

    internal class ViewHolder(var binding: ItemCardPreviewBinding) : RecyclerView.ViewHolder(binding.root)
}
