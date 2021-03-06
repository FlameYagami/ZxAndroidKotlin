package com.dab.zx.view.deck

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import com.dab.zx.R
import com.dab.zx.bean.DeckPreviewBean
import com.dab.zx.view.base.BaseRecyclerViewAdapter
import kotlinx.android.synthetic.main.item_deck_editor.view.*
import kotlinx.android.synthetic.main.item_deck_preview.view.*

internal class DeckPreviewAdapter(context: Context) : BaseRecyclerViewAdapter(context) {

    override val layoutId: Int
        get() = R.layout.item_deck_preview

    override fun getViewHolder(view: View): RecyclerView.ViewHolder {
        return ViewHolder(view)
    }

    override fun getView(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as ViewHolder
        val bean = data[position] as DeckPreviewBean
        with(viewHolder) {
            tvDeckName.text = bean.deckName
            tvStatusMain.text = bean.statusMain
            tvStatusExtra.text = bean.statusExtra
            tvStatusMain.setTextColor(if (bean.statusMain == context.getString(R.string.deck_pre_complete)) Color.GREEN else Color.RED)
            tvStatusExtra.setTextColor(if (bean.statusExtra == context.getString(R.string.deck_pre_complete)) Color.GREEN else Color.RED)
            linearLayout.setOnClickListener { itemClickListener?.invoke(linearLayout!!, data, position) }
            linearLayout.setOnLongClickListener {
                itemLongClickListener?.invoke(linearLayout, data, position)
                false
            }
            Glide.with(context).load(bean.playerPath).error(R.drawable.ic_unknown_picture).into(viewHolder.imgThumbnail)
        }
    }

    internal class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var linearLayout = view.viewItemContent
        var imgThumbnail = view.imgThumbnail
        var tvStatusMain = view.tvStatusMain
        var tvStatusExtra = view.tvStatusExtra
        var tvDeckName = view.tvDeckName
    }
}
