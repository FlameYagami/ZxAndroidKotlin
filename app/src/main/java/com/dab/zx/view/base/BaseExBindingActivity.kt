package com.dab.zx.view.base

import android.app.Activity
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.view.MotionEvent
import com.dab.zx.uitls.AppManager
import com.dab.zx.uitls.DisplayUtils
import com.michaelflisar.rxbus2.rx.RxDisposableManager


abstract class BaseExBindingActivity : Activity() {

    abstract val layoutId: Int

    abstract fun initViewAndData(mViewDataBinding: ViewDataBinding)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        StatusBarUtils.enableTranslucentStatusBar(this)
        initViewAndData(DataBindingUtil.setContentView(this, layoutId))
        AppManager.instance().addActivity(this)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (null != this.currentFocus) {
            DisplayUtils.hideKeyboard(this)
        }
        return super.onTouchEvent(event)
    }

    override fun onDestroy() {
        super.onDestroy()
        RxDisposableManager.unsubscribe(this)
        AppManager.instance().finishActivity(this)
    }
}
