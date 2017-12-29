package com.dab.zx.view.deck

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.FrameLayout
import com.bumptech.glide.Glide
import com.dab.zx.R
import com.dab.zx.bean.CardBean
import com.dab.zx.game.utils.CardUtils
import com.dab.zx.uitls.DisplayUtils
import com.dab.zx.view.base.BaseRecyclerViewAdapter
import kotlinx.android.synthetic.main.item_deck_editor.view.*

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
        with(viewHolder) {
            imgThumbnail.layoutParams = FrameLayout.LayoutParams(widthPx, heightPx)
            imgThumbnail.setOnClickListener { view -> itemClickListener?.invoke(view, data, position) }
            imgThumbnail.setOnLongClickListener { view ->
                itemLongClickListener?.invoke(view, data, position)
                false
            }
            imgRestrict.visibility = if (cardBean.restrict == "0") View.VISIBLE else View.GONE
            Glide.with(context).load(CardUtils.getImagePathList(cardBean.image)[0]).error(R.drawable.ic_unknown_thumbnail).into(viewHolder.imgThumbnail)
        }
    }

    internal class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgThumbnail = itemView.imgThumbnail
        var imgRestrict = itemView.imgRestrict
    }

    companion object {
        private val TAG = DeckAdapter::class.java.simpleName
    }
}
