package com.dab.zx.view.main

import android.databinding.ViewDataBinding
import com.dab.zx.R
import com.dab.zx.config.MapConst
import com.dab.zx.databinding.ActivityMainBinding
import com.dab.zx.uitls.AppManager
import com.dab.zx.view.base.BaseActivity
import com.dab.zx.viewmodel.MainVm
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private var firstTime: Long = 0
    private lateinit var mMainVm: MainVm

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun initViewAndData(dataBinding: ViewDataBinding) {
        // 主界面不可调用SwipeBack
        setSwipeBackEnable(false)
        mMainVm = MainVm()
        (dataBinding as ActivityMainBinding).vm = mMainVm
        bannerPack.apply {
            initScale()
            setImages(MapConst.GuideMap.entries.map { it.value })
            setOnBannerClickListener { position ->
                mMainVm.onPackQueryClick(position, this)
            }
            start()
        }
    }

    override fun onBackPressed() {
        val lastTime = System.currentTimeMillis()
        val between = lastTime - firstTime
        if (between < 2000) {
            AppManager.instance().AppExit(this)
        } else {
            firstTime = lastTime
            showSnackBar(getString(R.string.exit_with_more_back))
        }
    }
}
