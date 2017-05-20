package com.zx.ui.versus

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.zx.R
import com.zx.bean.ResourceBean
import com.zx.ui.base.BaseRecyclerViewAdapter
import com.zx.uitls.RotateTransformation

/**
 * Created by 八神火焰 on 2017/1/14.
 */

class ResourceAdapter(context: Context) : BaseRecyclerViewAdapter(context) {

    override val layoutId: Int
        get() = R.layout.item_duel_resource

    override fun getViewHolder(view: View): RecyclerView.ViewHolder {
        return ViewHolder(view)
    }

    override fun getView(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as ViewHolder
        val resourceBean = data[position] as ResourceBean
        if (resourceBean.isResourceVisible) {
            Glide.with(context).load(resourceBean.duelBean.thumbnailPath).error(R.drawable.ic_unknown_thumbnail).into(viewHolder.imgThumbnail!!)
        } else {
            Glide.with(context).load(resourceBean.duelBean.thumbnailPath).error(R.drawable.ic_unknown_thumbnail).transform(RotateTransformation(context, 90f)).into(viewHolder.imgThumbnail!!)
        }
    }

    internal class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @BindView(R.id.item_content)
        var viewContent: LinearLayout? = null
        @BindView(R.id.img_thumbnail)
        var imgThumbnail: ImageView? = null

        init {
            ButterKnife.bind(this, itemView)
        }
    }
}
