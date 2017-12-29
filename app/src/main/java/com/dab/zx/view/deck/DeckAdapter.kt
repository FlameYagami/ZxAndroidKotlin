package com.dab.zx.view.deck

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.FrameLayout
import com.bumptech.glide.Glide
import com.dab.zx.R
import com.dab.zx.bean.DeckBean
import com.dab.zx.uitls.DisplayUtils
import com.dab.zx.view.base.BaseRecyclerViewAdapter
import kotlinx.android.synthetic.main.item_deck_editor.view.*

/**
 * Created by 八神火焰 on 2016/12/22.
 */

internal class DeckAdapter(context: Context) : BaseRecyclerViewAdapter(context) {

    override val layoutId: Int
        get() = R.layout.item_deck_editor

    override fun getViewHolder(view: View): RecyclerView.ViewHolder {
        return ViewHolder(view)
    }

    override fun getView(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as ViewHolder
        val deckBean = data[position] as DeckBean
        val widthPx = (DisplayUtils.screenWidth - DisplayUtils.dip2px(15f)) / 10
        val heightPx = widthPx * 7 / 5
        with(viewHolder) {
            imgThumbnail.layoutParams = FrameLayout.LayoutParams(widthPx, heightPx)
            imgThumbnail.setOnClickListener { view -> itemClickListener?.invoke(view, data, holder.getAdapterPosition()) }
            imgThumbnail.setOnLongClickListener { view ->
                itemLongClickListener?.invoke(view, data, holder.getAdapterPosition())
                true
            }
            Glide.with(context).load(deckBean.imagePath).error(R.drawable.ic_unknown_thumbnail).centerCrop().into(viewHolder.imgThumbnail)
        }
    }

    internal class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgThumbnail = itemView.imgThumbnail
    }

    companion object {
        private val TAG = DeckAdapter::class.java.simpleName
    }
}
