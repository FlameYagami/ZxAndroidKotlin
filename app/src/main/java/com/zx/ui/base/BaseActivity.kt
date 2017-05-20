package com.zx.ui.base

import android.content.Context
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.design.widget.Snackbar
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import butterknife.BindView
import com.zx.R
import com.zx.config.MyApp
import com.zx.uitls.AppManager
import com.zx.uitls.DisplayUtils
import com.zx.uitls.RxBus
import com.zx.uitls.StatusBarUtils
import com.zx.view.dialog.DialogLoading
import com.zx.view.widget.AppBarView
import com.zx.view.widget.ToastView
import me.imid.swipebacklayout.lib.SwipeBackLayout

abstract class BaseActivity : SwipeBackActivity() {
    @BindView(R.id.viewAppBar)
    @Nullable
    internal var viewAppBar: AppBarView? = null

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

    /**
     * 初始化控件以及装填数据
     */
    abstract fun initViewAndData()

    abstract fun onNavigationClick()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSwipeBackLayout = swipeBackLayout
        (mSwipeBackLayout as SwipeBackLayout).setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT)
        StatusBarUtils.enableTranslucentStatusBar(this)
        setContentView(layoutId)
        initViewAndData()
        AppManager.getInstances().addActivity(this)
        viewAppBar?.setNavigationClickListener(object : AppBarView.NavigationClickListener {
            override fun onNavigationClick() {
                onNavigationClick()
            }
        })
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
