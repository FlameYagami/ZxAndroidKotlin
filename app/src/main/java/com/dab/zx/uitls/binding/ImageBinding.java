package com.dab.zx.uitls.binding;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dab.zx.R;
import com.dab.zx.game.utils.CardUtils;
import com.dab.zx.uc.widget.BannerExView;

import java.util.List;

/**
 * Created by 八神火焰 on 2017/12/29.
 */

public class ImageBinding {
    @BindingAdapter("bind:cardPreview")
    public static void setCardPreview(ImageView view, String imageJson) {
        Glide.with(view.getContext()).load(CardUtils.INSTANCE.getImagePathList(imageJson).get(0)).error(R.drawable.ic_unknown_picture).into(view);
    }

    @BindingAdapter("bind:sign")
    public static void setSignImage(ImageView view, int signResId) {
        if (-1 == signResId) {
            view.setImageBitmap(null);
        } else {
            Glide.with(view.getContext()).load(signResId).error(null).into(view);
        }
    }

    @BindingAdapter("bind:camp")
    public static void setCampImage(ImageView view, int campResId) {
        if (-1 == campResId) {
            view.setImageBitmap(null);
        } else {
            Glide.with(view.getContext()).load(campResId).error(null).into(view);
        }
    }

    @BindingAdapter("bind:bannerImages")
    public static void setImages(BannerExView bannerExView, List list) {
        bannerExView.setImages(list);
    }
}
