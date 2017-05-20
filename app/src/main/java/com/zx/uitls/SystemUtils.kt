package com.zx.uitls

import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager

import com.zx.R
import com.zx.bean.UpdateBean
import com.zx.config.MyApp
import com.zx.view.dialog.DialogConfirm

/**
 * Created by 八神火焰 on 2016/12/29.
 */
object SystemUtils {
    private val TAG = SystemUtils::class.java.simpleName

    /**
     * 获取当前系统版本号
     */
    // 获取PackageManager的实例
    // getPackageName()是你当前类的包名，0代表是获取版本信息
    val systemVersionCode: Int
        get() {
            var versionCode = 100
            val packageManager = MyApp.context?.packageManager
            val packInfo: PackageInfo
            try {
                packInfo = packageManager!!.getPackageInfo(MyApp.context?.packageName, 0)
                versionCode = packInfo.versionCode
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
                return versionCode
            }

            return versionCode
        }

    /**
     * 获取当前系统版本名称
     */
    // 获取PackageManager的实例
    // getPackageName()是你当前类的包名，0代表是获取版本信息
    val versionName: String
        get() {
            var versionName: String
            val packageManager = MyApp.context?.packageManager
            val packInfo: PackageInfo
            try {
                packInfo = packageManager!!.getPackageInfo(MyApp.context?.packageName, 0)
                versionName = packInfo.versionName
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
                versionName = "1.0.0"
            }

            return versionName
        }


    /**
     * 显示更新 daliog

     * @param context    上下文
     * *
     * @param updateBean 更新模型
     * *
     * @param type       0为静默检测更新使用，1为用户点击检测更新使用
     */
    fun showUpdateDialog(context: Context, updateBean: UpdateBean, type: Int) {
        // 版本号数小于已安装版本号数，则不用更新
        if (checkVersionCode(updateBean.versioncode)) {
            if (DialogConfirm.show(context, context.getString(R.string.update_is_update))) {
                //                IntentUtils.startUpdateService(mUpdateBean);
            } else {
                MyApp.mUpdateBean = null
            }
        } else if (type == 1) {
            AlertDialog.Builder(context)
                    .setTitle(context.getString(R.string.update_title))
                    .setMessage(context.getString(R.string.update_is_new))
                    .setCancelable(false)
                    .setPositiveButton("确定") { dialog, _ -> dialog.dismiss() }.create().show()
        }
    }

    /**
     * 判断版本号

     * @param versionCode 服务器文件的版本号
     * *
     * @return 比对结果
     */
    private fun checkVersionCode(versionCode: Int): Boolean {
        val systemVersionCode = systemVersionCode
        return versionCode > systemVersionCode
    }
}
