package com.zx.view.dialog

import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.zx.R
import com.zx.ui.base.BaseRecyclerViewAdapter
import com.zx.ui.deckeditor.DeckAdapter
import kotlinx.android.synthetic.main.dialog_checkbox.*
import kotlinx.android.synthetic.main.item_checkbox.view.*
import java.util.*

/**
 * Created by 八神火焰 on 2016/12/16.
 */

class DialogCheckBox(context: Context, title: String, mCheckboxMap: LinkedHashMap<String, Boolean>, mButtonClickListener: (mCheckboxMap: LinkedHashMap<String, Boolean>) -> Unit) : AlertDialog(context), DialogInterface.OnClickListener {
    private val mCheckBoxAdapter: CheckBoxAdapter

    var mButtonClickListener: ((mCheckboxMap: LinkedHashMap<String, Boolean>) -> Unit)

    init {
        val view = View.inflate(context, R.layout.dialog_checkbox, null)
        this.mButtonClickListener = mButtonClickListener
        setView(view)
        setTitle(title)
        setButton(DialogInterface.BUTTON_POSITIVE, "确 定", this)
        setButton(DialogInterface.BUTTON_NEGATIVE, "取 消", this)
        mCheckBoxAdapter = CheckBoxAdapter(context, mCheckboxMap)
        rvCheckbox.layoutManager = GridLayoutManager(context, 2)
//        rvCheckbox.adapter = mCheckBoxAdapter
    }

    override fun onClick(dialogInterface: DialogInterface, which: Int) {
        if (which == DialogInterface.BUTTON_POSITIVE) {
            mButtonClickListener.invoke(mCheckBoxAdapter.checkboxMap)
        }
    }

    private class CheckBoxAdapter constructor(context: Context, internal val checkboxMap: LinkedHashMap<String, Boolean>) : BaseRecyclerViewAdapter(context) {

        private val mKey = ArrayList<String>()

        override val layoutId: Int
            get() = R.layout.item_checkbox

        override fun getViewHolder(view: View): RecyclerView.ViewHolder {
            return DeckAdapter.ViewHolder(view)
        }

        override fun getView(holder: RecyclerView.ViewHolder, position: Int) {
            val viewHolder = holder as ViewHolder
            val key = mKey[position]
            with(viewHolder) {
                compatCheckBox.text = key
                compatCheckBox.isChecked = checkboxMap[key] as Boolean
                compatCheckBox.setOnCheckedChangeListener { _, _ -> checkboxMap.put(key, !checkboxMap[key]!!) }
            }
        }

        init {
            mKey.addAll(checkboxMap.keys)
        }
    }

    internal class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var compatCheckBox = itemView.checkbox
    }

    companion object {
        private val TAG = DialogCheckBox::class.java.simpleName
    }
}
