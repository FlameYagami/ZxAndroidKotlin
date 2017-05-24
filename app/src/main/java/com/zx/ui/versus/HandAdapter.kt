package com.zx.ui.versus

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import com.zx.R
import com.zx.bean.HandBean
import com.zx.ui.base.BaseRecyclerViewAdapter
import kotlinx.android.synthetic.main.item_duel_hand.view.*

/**
 * Created by 八神火焰 on 2016/12/26.
 */

class HandAdapter internal constructor(context: Context) : BaseRecyclerViewAdapter(context) {

    override val layoutId: Int
        get() = R.layout.item_duel_hand

    override fun getViewHolder(view: View): RecyclerView.ViewHolder {
        return ViewHolder(view)
    }

    override fun getView(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as ViewHolder
        val handBean = data[position] as HandBean

        viewHolder.viewTop?.visibility = if (handBean.isTopVisible) View.VISIBLE else View.GONE
        viewHolder.viewBottom?.visibility = if (handBean.isBottomVisible) View.VISIBLE else View.GONE
        viewHolder.imgThumbnail?.setOnClickListener { view ->
            setItemVisible(position)
            itemClickListener?.invoke(view, data, position)
        }
        Glide.with(context).load(handBean.duelBean.thumbnailPath).error(R.drawable.ic_unknown_picture).into(viewHolder.imgThumbnail!!)
    }

    private fun setItemVisible(position: Int) {
        for (i in data.indices) {
            val handBean = data[i] as HandBean
            if (i == position) {
                handBean.isTopVisible = !handBean.isTopVisible
                handBean.isBottomVisible = !handBean.isBottomVisible
            } else {
                handBean.isTopVisible = true
                handBean.isBottomVisible = false
            }
        }
        notifyDataSetChanged()
    }

    internal class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgThumbnail = itemView.img_thumbnail
        var viewTop = itemView.view_top
        var viewBottom = itemView.view_bottom
    }

    companion object {
        private val TAG = HandAdapter::class.java.simpleName
    }
}
