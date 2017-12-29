package com.dab.zx.uc.dialog

import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.support.v7.widget.AppCompatCheckBox
import android.support.v7.widget.RecyclerView
import android.view.View
import com.dab.zx.R
import com.dab.zx.view.base.BaseRecyclerViewAdapter
import kotlinx.android.synthetic.main.item_checkbox.view.*
import java.util.*


/**
 * Created by 八神火焰 on 2016/12/16.
 */

class DialogCheckBox(context: Context, title: String, mCheckboxMap: LinkedHashMap<String, Boolean>,
                     private var mButtonClickListener: (mCheckboxMap: LinkedHashMap<String, Boolean>) -> Unit) :
        AlertDialog(context), DialogInterface.OnClickListener {

    private lateinit var mCheckBoxAdapter: CheckBoxAdapter

    init {
//        val view = View.inflate(context, R.layout.dialog_checkbox, null)
//        val rvCheckbox = view.findViewById<RecyclerView>(R.id.rvCheckbox)
//        setView(view)
//        setTitle(title)
//        setButton(DialogInterface.BUTTON_POSITIVE, "确 定", this)
//        setButton(DialogInterface.BUTTON_NEGATIVE, "取 消", this)
//        mCheckBoxAdapter = CheckBoxAdapter(context, mCheckboxMap)
//        rvCheckbox.layoutManager = GridLayoutManager(context, 2)
//        rvCheckbox.adapter = mCheckBoxAdapter
//        mCheckBoxAdapter.updateData(mCheckboxMap.keys.toList())
    }

    override fun onClick(dialogInterface: DialogInterface, which: Int) {
        if (which == DialogInterface.BUTTON_POSITIVE) {
            mButtonClickListener.invoke(mCheckBoxAdapter.mCheckboxMap)
        }
    }

    class CheckBoxAdapter(context: Context, var mCheckboxMap: LinkedHashMap<String, Boolean>) : BaseRecyclerViewAdapter(context) {

        override val layoutId: Int
            get() = R.layout.item_checkbox

        override fun getViewHolder(view: View): RecyclerView.ViewHolder {
            return ViewHolder(view)
        }

        override fun getView(holder: RecyclerView.ViewHolder, position: Int) {
            val viewHolder = holder as ViewHolder
            val key = data[position].toString()
            with(viewHolder) {
                checkBox.text = key
                checkBox.isChecked = mCheckboxMap[key] as Boolean
                checkBox.setOnCheckedChangeListener { _, _ -> mCheckboxMap.put(key, !(mCheckboxMap[key] as Boolean)) }
            }
        }

        companion object {
            private val TAG = DialogCheckBox::class.java.simpleName
        }

        internal class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var checkBox: AppCompatCheckBox = itemView.checkbox
        }
    }
}
