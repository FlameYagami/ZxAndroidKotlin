package com.dab.zx.binding

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.dab.zx.R
import com.dab.zx.game.utils.CardUtils

/**
 * Created by 八神火焰 on 2018/1/13.
 */
@BindingAdapter("bind:imgCardPreview")
fun setCardPreview(view: ImageView, imageJson: String) {
    Glide.with(view.context).load(CardUtils.getImagePathList(imageJson)[0]).error(R.drawable.ic_unknown_picture).into(view)
}

@BindingAdapter("bind:signResId")
fun setSignImage(view: ImageView, signResId: Int) {
    if (-1 == signResId) {
        view.setImageBitmap(null)
    } else {
        Glide.with(view.context).load(signResId).error(null).into(view)
    }
}

@BindingAdapter("bind:campResId")
fun setCampImage(view: ImageView, campResId: Int) {
    if (-1 == campResId) {
        view.setImageBitmap(null)
    } else {
        Glide.with(view.context).load(campResId).error(null).into(view)
    }
}