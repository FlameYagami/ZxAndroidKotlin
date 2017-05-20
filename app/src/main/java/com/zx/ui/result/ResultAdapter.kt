package com.zx.ui.result

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.zx.R
import com.zx.bean.CardBean
import com.zx.game.utils.CardUtils
import com.zx.ui.base.BaseRecyclerViewAdapter

internal class ResultAdapter(context: Context) : BaseRecyclerViewAdapter(context) {

    override val layoutId: Int
        get() = R.layout.item_result

    override fun getViewHolder(view: View): RecyclerView.ViewHolder {
        return ViewHolder(view)
    }

    override fun getView(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as ViewHolder
        val cardBean = data[position] as CardBean
        viewHolder.tvCname?.text = cardBean.cName
        viewHolder.tvRace?.text = cardBean.race
        viewHolder.tvCamp?.text = cardBean.camp
        viewHolder.tvPower?.text = cardBean.power
        viewHolder.tvCost?.text = cardBean.cost
        viewHolder.linearLayout?.setOnClickListener { mOnItemClickListener.onItemClick(viewHolder.linearLayout!!, data, position) }
        viewHolder.imgRestrict?.visibility = if (cardBean.restrict == "0") View.VISIBLE else View.GONE
        Glide.with(context).load(CardUtils.getImagePathList(cardBean.image)[0]).error(R.drawable.ic_unknown_picture).into(viewHolder.imgThumbnail!!)
    }

    internal class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        @BindView(R.id.img_thumbnail)
        var imgThumbnail: ImageView? = null
        @BindView(R.id.img_restrict)
        var imgRestrict: ImageView? = null
        @BindView(R.id.tv_cname)
        var tvCname: TextView? = null
        @BindView(R.id.tv_race_result)
        var tvRace: TextView? = null
        @BindView(R.id.tv_camp_result)
        var tvCamp: TextView? = null
        @BindView(R.id.tv_power_result)
        var tvPower: TextView? = null
        @BindView(R.id.tv_cost_result)
        var tvCost: TextView? = null
        @BindView(R.id.linearLayout)
        var linearLayout: LinearLayout? = null

        init {
            ButterKnife.bind(this, view)
        }
    }
}
