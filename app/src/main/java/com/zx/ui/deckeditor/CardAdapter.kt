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
import com.zx.bean.CardBean
import com.zx.game.utils.CardUtils
import com.zx.ui.base.BaseRecyclerViewAdapter
import com.zx.uitls.DisplayUtils

/**
 * Created by 八神火焰 on 2016/12/26.
 */

class CardAdapter internal constructor(context: Context) : BaseRecyclerViewAdapter(context) {

    override val layoutId: Int
        get() = R.layout.item_deck_editor

    override fun getViewHolder(view: View): RecyclerView.ViewHolder {
        return ViewHolder(view)
    }

    override fun getView(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as ViewHolder
        val cardBean = data[position] as CardBean
        val widthPx = (DisplayUtils.screenWidth - DisplayUtils.dip2px(32f)) / 10
        val heightPx = widthPx * 7 / 5
        viewHolder.imgThumbnail?.layoutParams = FrameLayout.LayoutParams(widthPx, heightPx)
        viewHolder.imgThumbnail?.setOnClickListener { view -> mOnItemClickListener.onItemClick(view, data, position) }
        viewHolder.imgThumbnail?.setOnLongClickListener { view ->
            mOnItemLongClickListener.onItemLongClick(view, data, position)
            false
        }
        viewHolder.imgRestrict?.visibility = if (cardBean.restrict == "0") View.VISIBLE else View.GONE
        Glide.with(context).load(CardUtils.getImagePathList(cardBean.image)[0]).error(R.drawable.ic_unknown_thumbnail).into(viewHolder.imgThumbnail!!)
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
