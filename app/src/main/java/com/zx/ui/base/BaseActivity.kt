package com.zx.ui.base

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
import com.zx.view.dialog.DialogLoadingUtils
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
        DialogLoadingUtils.show(this, message)
    }

    protected fun hideDialog() {
        DialogLoadingUtils.hide()
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
        AppManager.instance().addActivity(this)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (null != this.currentFocus) {
            DisplayUtils.hideKeyboard(this)
        }
        return super.onTouchEvent(event)
    }

    override fun onBackPressed() {
        mSwipeBackLayout?.scrollToFinishActivity()

    }

    override fun onDestroy() {
        super.onDestroy()
        RxDisposableManager.unsubscribe(this)
        AppManager.instance().finishActivity(this)
    }
}
