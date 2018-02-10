package com.dab.zx.view.main

import android.databinding.ViewDataBinding
import com.dab.zx.R
import com.dab.zx.databinding.ActivityMainBinding
import com.dab.zx.uitls.AppManager
import com.dab.zx.view.base.BaseBindingActivity
import com.dab.zx.viewmodel.MainVm

class MainActivity : BaseBindingActivity() {

    private var firstTime: Long = 0

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun initViewAndData(mViewDataBinding: ViewDataBinding) {
        // 主界面不可调用SwipeBack
        setSwipeBackEnable(false)
        (mViewDataBinding as ActivityMainBinding).vm = MainVm(this)
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
