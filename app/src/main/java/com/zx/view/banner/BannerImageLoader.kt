package com.zx.view.banner

import android.content.Context
import android.widget.ImageView

import com.bumptech.glide.Glide
import com.youth.banner.loader.ImageLoader
import com.zx.R

/**
 * Created by 八神火焰 on 2016/12/26.
 */

class BannerImageLoader : ImageLoader() {
    override fun displayImage(context: Context, path: Any, imageView: ImageView) {
        Glide.with(context).load(path).fitCenter().error(R.drawable.ic_unknown_picture).into(imageView)
    }
}
