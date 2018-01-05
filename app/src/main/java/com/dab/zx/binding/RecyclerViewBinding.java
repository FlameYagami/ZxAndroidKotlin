package com.dab.zx.binding;

import android.databinding.BindingAdapter;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dab.zx.binding.annotation.HeaderResHolder;
import com.dab.zx.binding.annotation.ResUtils;
import com.dab.zx.binding.annotation.SubitemResHolder;

import java.util.List;

/**
 * Created by 八神火焰 on 2017/12/29.
 */

public class RecyclerViewBinding {

    private static final String TAG = RecyclerViewBinding.class.getSimpleName();

    @BindingAdapter(value = {"bind:multiVm", "bind:multiData"})
    public static <HeaderView extends ViewDataBinding, SubitemView extends ViewDataBinding, R, T>
    void setMultiData(RecyclerView recyclerView, MultiItemVm<HeaderView, SubitemView, R, T> vm, List<T> datas) {
        if (vm == null) {
            return;
        }
        ItemView header  = ResUtils.INSTANCE.getItemView(vm.getClass().getAnnotation(HeaderResHolder.class));
        ItemView subitem = ResUtils.INSTANCE.getItemView(vm.getClass().getAnnotation(SubitemResHolder.class));
        if (null == header) {
            throw new IllegalArgumentException(TAG + "Header is null, maybe you forget @HeaderResHolder(R.layout.XXX) in " + vm.getClass().getCanonicalName());
        }
        if (null == subitem) {
            throw new IllegalArgumentException(TAG + "Subitem is null, maybe you forget @SubitemResHolder(R.layout.XXX) in " + vm.getClass().getCanonicalName());
        }
        if (null == recyclerView.getLayoutManager()) {
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        }
        MultiItemAdapter<HeaderView, SubitemView, R, T> adapter = vm.getAdapter();
        adapter.lateInit(datas, vm, header, subitem);
        recyclerView.setAdapter(adapter);
    }


    @BindingAdapter(value = {"bind:singleVm", "bind:singleData"})
    public static <SubitemView extends ViewDataBinding, T> void setSingleData(RecyclerView recyclerView, SingleItemVm<SubitemView, T> vm, List<T> datas) {
        if (vm == null) {
            return;
        }
        ItemView subitem = ResUtils.INSTANCE.getItemView(vm.getClass().getAnnotation(SubitemResHolder.class));
        if (null == subitem) {
            throw new IllegalArgumentException(TAG + "Subitem is null, maybe you forget @SubitemResHolder(R.layout.XXX) in " + vm.getClass().getCanonicalName());
        }
        if (null == recyclerView.getLayoutManager()) {
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        }
        SingleItemAdapter<SubitemView, T> adapter = vm.getAdapter();
        adapter.lateInit(datas, vm, subitem);
        recyclerView.setAdapter(adapter);
    }
}
