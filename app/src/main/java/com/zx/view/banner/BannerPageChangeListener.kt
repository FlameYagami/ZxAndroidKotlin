package com.zx.view.banner

import android.support.v4.view.ViewPager

/**
 * Created by 八神火焰 on 2016/12/26.
 */

class BannerPageChangeListener : ViewPager.OnPageChangeListener {
    internal var currentIndex = 1

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
