package com.dab.zx.binding

import android.databinding.BindingAdapter
import android.databinding.InverseBindingAdapter
import android.support.v7.widget.AppCompatSpinner
import android.widget.ArrayAdapter


/**
 * Created by 八神火焰 on 2018/1/18.
 */
@BindingAdapter(value = ["bind:spinnerAdapter"])
fun setAdapter(view: AppCompatSpinner, arrayAdapter: ArrayAdapter<String>) {
    view.adapter = arrayAdapter
}

@InverseBindingAdapter(attribute = "selectedValue", event = "selectedValueAttrChanged")
fun captureSelectedValue(view: AppCompatSpinner): String {
    return view.selectedItem as String
}