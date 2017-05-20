package com.zx.uitls

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import com.zx.config.MyApp.Companion.context

/**
 * Created by Administrator on 2016/5/12.
 */
object DisplayUtils {
    /**
     * 获取通知栏高度

     * @return 高度
     */
    //获取status_bar_height资源的ID
    //根据资源ID获取响应的尺寸值
    val statusBarHeight: Int
        get() {
            var statusBarHeight = -1
            val resourceId = context?.resources?.getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId!! > 0) {
                statusBarHeight = context?.resources?.getDimensionPixelSize(resourceId) as Int
            }
            return statusBarHeight
        }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)

     * @param dpValue dp
     * *
     * @return px
     */
    fun dip2px(dpValue: Float): Int {
        val scale = context?.resources?.displayMetrics?.density
        return (dpValue  * scale as Float + 0.5f).toInt()
    }


    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp

     * @param pxValue 像素大小
     * *
     * @return dp
     */
    fun px2dip(pxValue: Float): Int {
        val scale = context?.resources?.displayMetrics?.density
        return (pxValue / scale as Float  + 0.5f).toInt()
    }

    /**
     * 获取屏幕宽度

     * @return 宽度px
     */
    val screenWidth: Int
        get() {
            val screenWidth = context?.resources?.displayMetrics?.widthPixels
            return screenWidth as Int
        }

    fun hideKeyboard(context: Context) {
        val view = (context as Activity).window.peekDecorView()
        if (view != null) {
            val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}
