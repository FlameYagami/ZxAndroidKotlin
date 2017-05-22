package com.zx.ui.base

import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import com.zx.config.MyApp
import com.zx.uitls.AppManager
import com.zx.uitls.DisplayUtils
import com.zx.uitls.RxBus
import com.zx.uitls.StatusBarUtils
import com.zx.view.dialog.DialogLoading
import com.zx.view.widget.ToastView
import me.imid.swipebacklayout.lib.SwipeBackLayout

abstract class BaseActivity : SwipeBackActivity() {

    protected var mSwipeBackLayout: SwipeBackLayout? = null

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
        mSwipeBackLayout = swipeBackLayout
        (mSwipeBackLayout as SwipeBackLayout).setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT)
        StatusBarUtils.enableTranslucentStatusBar(this)
        setContentView(layoutId)
        initViewAndData()
        AppManager.getInstances().addActivity(this)
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
        DialogLoading.hideDialog()
        mSwipeBackLayout?.scrollToFinishActivity()

    }

    override fun onDestroy() {
        super.onDestroy()
        RxBus.instance.unSubscribe(this)
        AppManager.getInstances().finishActivity(this)
    }
}
