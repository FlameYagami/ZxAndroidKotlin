package com.zx.view.dialog

import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.support.v7.widget.AppCompatCheckBox
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zx.R
import kotlinx.android.synthetic.main.dialog_checkbox.*
import kotlinx.android.synthetic.main.item_checkbox.view.*
import java.util.*

/**
 * Created by 八神火焰 on 2016/12/16.
 */

class DialogCheckBox(context: Context, title: String, mCheckboxMap: LinkedHashMap<String, Boolean>, mListener: (mCheckboxMap: LinkedHashMap<String, Boolean>) -> Unit) : AlertDialog(context), DialogInterface.OnClickListener {
    private val mCheckBoxAdapter: CheckBoxAdapter

    var OnButtonClick: ((mCheckboxMap: LinkedHashMap<String, Boolean>) -> Unit)? = null

    init {
        val view = View.inflate(context, R.layout.dialog_checkbox, null)
        setView(view)
        setTitle(title)
        setButton(DialogInterface.BUTTON_POSITIVE, "确 定", this)
        setButton(DialogInterface.BUTTON_NEGATIVE, "取 消", this)
        rv_checkbox.layoutManager = GridLayoutManager(context, 2)
        mCheckBoxAdapter = CheckBoxAdapter(context, mCheckboxMap)
        rv_checkbox.adapter = mCheckBoxAdapter
        OnButtonClick = mListener
    }

    override fun onClick(dialogInterface: DialogInterface, which: Int) {
        if (which == DialogInterface.BUTTON_POSITIVE) {
            OnButtonClick?.invoke(mCheckBoxAdapter.checkboxMap)
        }
    }

    private inner class CheckBoxAdapter internal constructor(context: Context, internal val checkboxMap: LinkedHashMap<String, Boolean>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
        private val mKey = ArrayList<String>()

        init {
            mKey.addAll(checkboxMap.keys)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view = layoutInflater.inflate(R.layout.item_checkbox, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val viewHolder = holder as ViewHolder
            val key = mKey[position]
            with(viewHolder) {
                compatCheckBox?.text = key
                compatCheckBox?.isChecked = checkboxMap[key]!!
                compatCheckBox?.setOnCheckedChangeListener { _, _ -> checkboxMap.put(key, !checkboxMap[key]!!) }
            }
        }

        override fun getItemCount(): Int {
            return checkboxMap.size
        }
    }

    internal class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var compatCheckBox: AppCompatCheckBox? = itemView.checkbox
    }

    companion object {
        private val TAG = DialogCheckBox::class.java.simpleName
    }
}
