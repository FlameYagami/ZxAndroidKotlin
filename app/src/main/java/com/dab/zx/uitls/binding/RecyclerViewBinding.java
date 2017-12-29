package com.dab.zx.uitls.binding;

import android.databinding.BindingAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dab.zx.uitls.LogUtils;
import com.dab.zx.view.base.BaseRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 八神火焰 on 2017/12/29.
 */

public class RecyclerViewBinding {

    @BindingAdapter("bind:layoutManager")
    public static void setLayoutManager(RecyclerView recyclerView, RecyclerView.LayoutManager layout) {
        if (null == layout) {
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        } else {
            recyclerView.setLayoutManager(layout);
        }
    }

    @BindingAdapter("bind:adapter")
    public static void setAdapter(final RecyclerView recyclerView, BaseRecyclerViewAdapter adapter) {
        if (null == adapter) {
            throw new IllegalArgumentException("adapter is null ,maybe you forget binding it in the xml");
        }
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter("bind:data")
    public static <M> void setData(final RecyclerView recyclerView, List<M> data) {
        if (null == data) {
            data = new ArrayList<>();
        }
        LogUtils.INSTANCE.e("setData", data.size());
        ((BaseRecyclerViewAdapter)recyclerView.getAdapter()).updateData(data);
    }
}
