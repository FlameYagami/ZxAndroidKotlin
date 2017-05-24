package com.zx.ui.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import com.michaelflisar.rxbus2.rx.RxDisposableManager
import com.zx.config.MyApp
import com.zx.uitls.AppManager
import com.zx.uitls.DisplayUtils
import com.zx.uitls.StatusBarUtils
import com.zx.view.dialog.DialogLoading
import com.zx.view.widget.ToastView

abstract class BaseExActivity : Activity() {

    protected fun showToast(message: String) {
        runOnUiThread { ToastView.make(MyApp.context as Context, message, Toast.LENGTH_SHORT).show() }
    }

    protected fun showSnackBar(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }

    protected fun showDialog(message: String) {
        DialogLoading.showDialog(this, message, false)
    }

    protected fun hideDialog() {
        DialogLoading.hideDialog()
    }

    abstract val layoutId: Int

    abstract fun initViewAndData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtils.enableTranslucentStatusBar(this)
        setContentView(layoutId)
        initViewAndData()
        AppManager.instance().addActivity(this)
    }

    /**
     * 点击空白位置 隐藏软键盘
     */
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (null != this.currentFocus) {
            DisplayUtils.hideKeyboard(this)
        }
        return super.onTouchEvent(event)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        DialogLoading.hideDialog()
    }

    override fun onDestroy() {
        super.onDestroy()
        RxDisposableManager.unsubscribe(this)
        AppManager.instance().finishActivity(this)
    }

    override fun finish() {
        super.finish()
    }
}
