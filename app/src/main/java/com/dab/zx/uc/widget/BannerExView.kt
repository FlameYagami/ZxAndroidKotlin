package com.dab.zx.uc.widget

import android.content.Context
import android.databinding.BindingAdapter
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.dab.zx.R
import com.dab.zx.uitls.DisplayUtils
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.youth.banner.listener.OnBannerClickListener
import com.youth.banner.loader.ImageLoader
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

/**
 * Created by 八神火焰 on 2017/12/26.
 */
class BannerExView(context: Context, attrs: AttributeSet) : Banner(context, attrs) {

    private var enableScale: Boolean
    private var scaleRatio: Float

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BannerExView)
        val indicatorGravity = typedArray.getInt(R.styleable.BannerExView_indicator_gravity, BannerConfig.RIGHT)
        val bannerStyle = typedArray.getInt(R.styleable.BannerExView_banner_style, BannerConfig.CIRCLE_INDICATOR)
        val enableGlide = typedArray.getBoolean(R.styleable.BannerExView_enable_glide, true)
        enableScale = typedArray.getBoolean(R.styleable.BannerExView_enable_scale, false)
        scaleRatio = typedArray.getFloat(R.styleable.BannerExView_scale_ratio, 1.toFloat())
        typedArray.recycle()

        setIndicatorGravity(indicatorGravity)
        setBannerStyle(bannerStyle)
        if (enableGlide) {
            setImageLoader(GlideLoader())
        }

    }

    fun enableScale(enableScale: Boolean) {
        if (enableScale) {
            val heightPx = ((DisplayUtils.screenWidth - DisplayUtils.dip2px(32.toFloat())) * scaleRatio).toInt()
            this.layoutParams = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, heightPx)
        }
    }

    class GlideLoader : ImageLoader() {
        override fun displayImage(context: Context, path: Any, imageView: ImageView) {
            Glide.with(context).load(path).fitCenter().error(R.drawable.ic_unknown_picture).into(imageView)
        }
    }

    open class PageListener : ViewPager.OnPageChangeListener {
        private var currentIndex = 1

        fun getCurrentIndex(): Int {
            return currentIndex - 1
        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

        }

        override fun onPageSelected(position: Int) {
            currentIndex = position
        }

        override fun onPageScrollStateChanged(state: Int) {

        }
    }
}

@BindingAdapter("bind:delayStart")
fun onDelayStart(bannerExView: BannerExView, delay: Int) {
    Observable.timer(delay.toLong(), TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe {
        bannerExView.start()
    }
}

@BindingAdapter("bind:bannerImages")
fun setImages(bannerExView: BannerExView, list: List<*>) {
    bannerExView.setImages(list)
}

@BindingAdapter("bind:enableScale")
fun enableScale(bannerExView: BannerExView, enableScale: Boolean) {
    bannerExView.enableScale(enableScale)
}

@BindingAdapter("bind:bannerListener")
fun setBannerListener(bannerExView: BannerExView, listener: OnBannerClickListener) {
    bannerExView.setOnBannerClickListener(listener)
}