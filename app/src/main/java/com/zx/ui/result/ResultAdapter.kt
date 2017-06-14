package com.zx.ui.result

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import com.zx.R
import com.zx.bean.CardBean
import com.zx.game.utils.CardUtils
import com.zx.ui.base.BaseRecyclerViewAdapter
import kotlinx.android.synthetic.main.item_result.view.*

internal class ResultAdapter(context: Context) : BaseRecyclerViewAdapter(context) {

    override val layoutId: Int
        get() = R.layout.item_result

    override fun getViewHolder(view: View): RecyclerView.ViewHolder {
        return ViewHolder(view)
    }

    override fun getView(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as ViewHolder
        val cardBean = data[position] as CardBean
        with(viewHolder) {
            tvCname.text = cardBean.cName
            tvRace.text = cardBean.race
            tvCamp.text = cardBean.camp
            tvPower.text = cardBean.power
            tvCost.text = cardBean.cost
            linearLayout.setOnClickListener { itemClickListener?.invoke(linearLayout, data, position) }
            imgRestrict.visibility = if (cardBean.restrict == "0") View.VISIBLE else View.GONE
            Glide.with(context).load(CardUtils.getImagePathList(cardBean.image)[0]).error(R.drawable.ic_unknown_picture).into(viewHolder.imgThumbnail)
        }
    }

    internal class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imgRestrict = view.imgRestrict
        var imgThumbnail = view.imgThumbnail
        var tvCname = view.tvCname
        var tvRace = view.tvRace
        var tvCamp = view.tv_camp
        var tvPower = view.tvPower
        var tvCost = view.tvCost
        var linearLayout = view.linearLayout
    }
}
