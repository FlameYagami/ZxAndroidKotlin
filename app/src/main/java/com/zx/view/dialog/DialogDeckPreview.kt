package com.zx.view.dialog

import android.content.Context
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.widget.GridLayoutManager
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
import com.zx.game.utils.DeckUtils
import com.zx.ui.base.BaseRecyclerViewAdapter

/**
 * Created by 八神火焰 on 2017/2/8.
 */

class DialogDeckPreview(context: Context, private val onDeckClick: (Any, Any) -> Unit) : AlertDialog(context) {

    interface OnDeckClick {
        fun getDeck(dialog: DialogDeckPreview, bean: DeckPreviewBean)
    }

    init {
        val view = View.inflate(context, R.layout.dialog_deck_preview, null)
        ButterKnife.bind(this, view)

        setView(view)
        setTitle("卡组选择")
        val mRecyclerView = view.findViewById(R.id.recyclerview) as RecyclerView
        mRecyclerView.layoutManager = GridLayoutManager(context, 5)
        val mRecyclerViewAdapter = RecyclerViewAdapter(context)
        mRecyclerView.adapter = mRecyclerViewAdapter
        mRecyclerViewAdapter.setOnItemClickListener { _: View, _: List<*>, _: Int -> this::onItemClick }
    }

    fun onItemClick(view: View, data: List<*>, position: Int) {
        val bean = data[position] as DeckPreviewBean
        if (!DeckUtils.checkDeck(bean.numberExList)) {
            Snackbar.make(view, "卡组不符合标准", Snackbar.LENGTH_SHORT).show()
            return
        }
//        onDeckClick.getDeck(this, bean)
        dismiss()
    }

    private open inner class RecyclerViewAdapter internal constructor(context: Context) : BaseRecyclerViewAdapter(context) {
        init {
            updateData(DeckUtils.deckPreviewList)
        }

        override val layoutId: Int
            get() = R.layout.item_dialog_deck_preview

        override fun getViewHolder(view: View): RecyclerView.ViewHolder {
            return ViewHolder(view)
        }

        override fun getView(holder: RecyclerView.ViewHolder, position: Int) {
            val viewHolder = holder as ViewHolder
            val bean = data[position] as DeckPreviewBean
            viewHolder.viewContent?.setOnClickListener { itemClickListener?.invoke(viewHolder.viewContent!!, data, position) }
            viewHolder.textView?.text = bean.deckName
            Glide.with(context).load(bean.playerPath).error(R.drawable.ic_unknown_picture).into(viewHolder.imageView!!)
        }
    }

    internal class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @BindView(R.id.view_content)
        var viewContent: LinearLayout? = null
        @BindView(R.id.imageView)
        var imageView: ImageView? = null
        @BindView(R.id.textView)
        var textView: TextView? = null

        init {
            ButterKnife.bind(this, itemView)
        }
    }
}
