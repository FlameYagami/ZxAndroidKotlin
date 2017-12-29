package com.dab.zx.uitls.binding;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dab.zx.R;
import com.dab.zx.game.utils.CardUtils;

/**
 * Created by 八神火焰 on 2017/12/29.
 */

public class ImageBinding {
    @BindingAdapter("bind:cardPreview")
    public static void setCardPreview(ImageView view, String imageJson) {
        Glide.with(view.getContext()).load(CardUtils.INSTANCE.getImagePathList(imageJson).get(0)).error(R.drawable.ic_unknown_picture).into(view);
    }
}
