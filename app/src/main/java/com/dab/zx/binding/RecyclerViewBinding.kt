package com.dab.zx.binding

import android.databinding.BindingAdapter
import android.databinding.ViewDataBinding
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.dab.zx.binding.RecyclerViewBinding.Companion.TAG
import com.dab.zx.binding.annotation.HeaderResHolder
import com.dab.zx.binding.annotation.ResUtils
import com.dab.zx.binding.annotation.SubitemResHolder

/**
 * Created by 八神火焰 on 2018/1/15.
 */
open class RecyclerViewBinding{
    companion object {
        val TAG: String = RecyclerViewBinding::class.java.simpleName
    }
}

@BindingAdapter(value = ["bind:layoutManager"])
fun setLayoutManager(recyclerView: RecyclerView, layoutManager: RecyclerView.LayoutManager){
    recyclerView.layoutManager = layoutManager
}

@BindingAdapter(value = ["bind:multiVm", "bind:multiData"])
fun <HeaderView : ViewDataBinding, SubitemView : ViewDataBinding, R, T> setMultiData(recyclerView: RecyclerView, vm: MultiItemVm<HeaderView, SubitemView, R, T>?, datas: List<T>) {
    if (vm == null) {
        return
    }
    val header = ResUtils.getItemView(vm.javaClass.getAnnotation(HeaderResHolder::class.java))
    val subitem = ResUtils.getItemView(vm.javaClass.getAnnotation(SubitemResHolder::class.java))
    if (null == header) {
        throw IllegalArgumentException(TAG + "Header is null, maybe you forget @HeaderResHolder(R.layout.XXX) in " + vm.javaClass.canonicalName)
    }
    if (null == subitem) {
        throw IllegalArgumentException(TAG + "Subitem is null, maybe you forget @SubitemResHolder(R.layout.XXX) in " + vm.javaClass.canonicalName)
    }
    if (null == recyclerView.layoutManager) {
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
    }
    val adapter = vm.adapter
    adapter.lateInit(datas, vm, header, subitem)
    recyclerView.adapter = adapter
}


@BindingAdapter(value = ["bind:singleVm", "bind:singleData"])
fun <SubitemView : ViewDataBinding, T> setSingleData(recyclerView: RecyclerView, vm: SingleItemVm<SubitemView, T>?, datas: List<T>) {
    if (vm == null) {
        return
    }
    val subitem = ResUtils.getItemView(vm.javaClass.getAnnotation(SubitemResHolder::class.java)) ?: throw IllegalArgumentException(TAG + "Subitem is null, maybe you forget @SubitemResHolder(R.layout.XXX) in " + vm.javaClass.canonicalName)
    if (null == recyclerView.layoutManager) {
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
    }
    val adapter = vm.adapter
    adapter.lateInit(datas, vm, subitem)
    recyclerView.adapter = adapter
}