package com.zx.ui.deckpreview

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.zx.R
import com.zx.bean.DeckPreviewBean
import com.zx.ui.base.BaseRecyclerViewAdapter

internal class DeckPreviewAdapter(context: Context) : BaseRecyclerViewAdapter(context) {

    override val layoutId: Int
        get() = R.layout.item_deck_preview

    override fun getViewHolder(view: View): RecyclerView.ViewHolder {
        return ViewHolder(view)
    }

    override fun getView(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as ViewHolder
        val bean = data[position] as DeckPreviewBean
        viewHolder.tvDeckName?.text = bean.deckName
        viewHolder.tvStatusMain?.text = bean.statusMain
        viewHolder.tvStatusExtra?.text = bean.statusExtra
        viewHolder.tvStatusMain?.setTextColor(if (bean.statusMain == context.getString(R.string.deck_complete)) Color.GREEN else Color.RED)
        viewHolder.tvStatusExtra?.setTextColor(if (bean.statusExtra == context.getString(R.string.deck_complete)) Color.GREEN else Color.RED)
        viewHolder.linearLayout?.setOnClickListener { itemClickListener?.invoke(viewHolder.linearLayout!!, data, position) }
        viewHolder.linearLayout?.setOnLongClickListener {
            itemLongClickListener?.invoke(viewHolder.linearLayout as LinearLayout, data, position)
            false
        }
        Glide.with(context).load(bean.playerPath).error(R.drawable.ic_unknown_picture).into(viewHolder.imgThumbnail!!)
    }

    internal class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        @BindView(R.id.linearLayout)
        var linearLayout: LinearLayout? = null
        @BindView(R.id.img_thumbnail)
        var imgThumbnail: ImageView? = null
        @BindView(R.id.tv_status_main)
        var tvStatusMain: TextView? = null
        @BindView(R.id.tv_status_extra)
        var tvStatusExtra: TextView? = null
        @BindView(R.id.tv_deck_name)
        var tvDeckName: TextView? = null

        init {
            ButterKnife.bind(this, view)
        }
    }
}
