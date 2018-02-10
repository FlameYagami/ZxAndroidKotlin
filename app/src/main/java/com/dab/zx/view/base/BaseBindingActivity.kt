package com.dab.zx.view.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.dab.zx.uc.widget.ToastView
import com.dab.zx.uitls.AppManager
import com.dab.zx.uitls.DisplayUtils
import com.michaelflisar.rxbus2.rx.RxDisposableManager
import me.imid.swipebacklayout.lib.SwipeBackLayout


abstract class BaseBindingActivity : SwipeBackActivity() {

    lateinit var mSwipeBackLayout: SwipeBackLayout

    abstract val layoutId: Int

    abstract fun initViewAndData(mViewDataBinding: ViewDataBinding)

    private fun getDecorView(): ViewGroup {
        return findViewById(android.R.id.content)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSwipeBackLayout = swipeBackLayout
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT)
        initViewAndData(DataBindingUtil.setContentView(this, layoutId))
        AppManager.instance().addActivity(this)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (null != this.currentFocus) {
            DisplayUtils.hideKeyboard(this)
        }
        return super.onTouchEvent(event)
    }

    override fun onBackPressed() {
        mSwipeBackLayout.scrollToFinishActivity()
    }

    override fun onDestroy() {
        super.onDestroy()
        RxDisposableManager.unsubscribe(this)
        AppManager.instance().finishActivity(this)
    }

    fun showToast(message: String) {
        runOnUiThread { ToastView.make(getDecorView().context, message, Toast.LENGTH_SHORT).show() }
    }

    fun showSnackBar(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }

    fun showSnackBar(message: String) {
        Snackbar.make(getDecorView(), message, Snackbar.LENGTH_SHORT).show()
    }
}
