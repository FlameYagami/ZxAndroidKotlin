package com.dab.zx.uc.dialog

import android.content.Context
import android.content.DialogInterface
import android.databinding.DataBindingUtil
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import com.dab.zx.R
import com.dab.zx.binding.SingleItemVm
import com.dab.zx.binding.annotation.SubitemResHolder
import com.dab.zx.databinding.DialogBindingCheckboxBinding
import com.dab.zx.databinding.ItemBindingCheckboxBinding
import java.util.*


/**
 * Created by 八神火焰 on 2016/12/16.
 */

class DialogBindingCheckBox(context: Context, title: String, mCheckboxMap: LinkedHashMap<String, Boolean>,
                            private var mButtonClickListener: (mCheckboxMap: LinkedHashMap<String, Boolean>) -> Unit) :
        AlertDialog(context), DialogInterface.OnClickListener {

    var vm : DialogCheckboxVm

    init {
        val binding = DataBindingUtil.inflate<DialogBindingCheckboxBinding>(LayoutInflater.from(context), R.layout.dialog_binding_checkbox, null, false)
        setView(binding.root)
        vm = DialogCheckboxVm(context, mCheckboxMap)
        binding.vm = vm
        setTitle(title)
        setButton(DialogInterface.BUTTON_POSITIVE, "确 定", this)
        setButton(DialogInterface.BUTTON_NEGATIVE, "取 消", this)
    }

    override fun onClick(dialogInterface: DialogInterface, which: Int) {
        if (which == DialogInterface.BUTTON_POSITIVE) {
            mButtonClickListener.invoke(vm.mCheckboxMap)
        }
    }
}

@SubitemResHolder(R.layout.item_binding_checkbox)
class DialogCheckboxVm(context: Context, var mCheckboxMap: LinkedHashMap<String, Boolean>) : SingleItemVm<ItemBindingCheckboxBinding, CheckboxModel>(context) {

    init {
        setOnItemClickListener { _, data, position ->
            val checkboxModel = data[position]
            mCheckboxMap.put(checkboxModel.key, !(mCheckboxMap[checkboxModel.key] as Boolean))
        }
        val list = mCheckboxMap.map { CheckboxModel(it.key, it.value) }.toMutableList()
        data.addAll(list)
    }
}

open class CheckboxModel(var key: String, var value: Boolean)
