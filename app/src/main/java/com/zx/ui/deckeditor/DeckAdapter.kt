package com.zx.ui.deckeditor

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.zx.R
import com.zx.bean.DeckBean
import com.zx.ui.base.BaseRecyclerViewAdapter
import com.zx.uitls.DisplayUtils

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
        viewHolder.imgThumbnail?.layoutParams = FrameLayout.LayoutParams(widthPx, heightPx)
        viewHolder.imgThumbnail?.setOnClickListener { view -> mOnItemClickListener.onItemClick(view, data, holder.getAdapterPosition()) }
        viewHolder.imgThumbnail?.setOnLongClickListener { view ->
            mOnItemLongClickListener.onItemLongClick(view, data, holder.getAdapterPosition())
            true
        }
        Glide.with(context).load(deckBean.imagePath).error(R.drawable.ic_unknown_thumbnail).centerCrop().into(viewHolder.imgThumbnail!!)
    }

    internal class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @BindView(R.id.img_thumbnail)
        var imgThumbnail: ImageView? = null
        @BindView(R.id.img_restrict)
        var imgRestrict: ImageView? = null

        init {
            ButterKnife.bind(this, itemView)
        }
    }

    companion object {
        private val TAG = DeckAdapter::class.java.simpleName
    }
}
