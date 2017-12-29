package com.dab.zx.view.base

import android.app.Activity
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import com.dab.zx.config.MyApp
import com.dab.zx.uc.dialog.DialogLoadingUtils
import com.dab.zx.uc.widget.ToastView
import com.dab.zx.uitls.AppManager
import com.dab.zx.uitls.DisplayUtils
import com.michaelflisar.rxbus2.rx.RxDisposableManager


abstract class BaseExActivity : Activity() {

    private var mViewDataBinding: ViewDataBinding? = null

    protected fun showToast(message: String) {
        runOnUiThread { ToastView.make(MyApp.context, message, Toast.LENGTH_SHORT).show() }
    }

    protected fun showSnackBar(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }

    protected fun showDialog(message: String) {
        DialogLoadingUtils.show(this, message)
    }

    protected fun hideDialog() {
        DialogLoadingUtils.hide()
    }

    abstract val layoutId: Int

    abstract fun initViewAndData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        StatusBarUtils.enableTranslucentStatusBar(this)
        mViewDataBinding = DataBindingUtil.setContentView(this, layoutId)
        initViewAndData()
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
