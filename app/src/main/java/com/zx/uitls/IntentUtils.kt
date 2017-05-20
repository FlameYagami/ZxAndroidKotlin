package com.zx.uitls

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.zx.config.MyApp.Companion.context


/**
 * Intent跳转工具类

 * @version 1.0
 * *
 * @Description 用于简化页面跳转搞得重复代码
 */
object IntentUtils {
    /**
     * 普通的方式打开一个Activiy

     * @param context   上下文
     * *
     * @param gotoClass 需要打开的Activity
     * *
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    fun gotoActivity(context: Context, gotoClass: Class<*>) {
        val intent = Intent()
        intent.setClass(context, gotoClass)
        context.startActivity(intent)
    }

    /**
     * 普通的方式打开一个activity，并携带数据

     * @param context   上下文
     * *
     * @param gotoClass 需要打开的Activity
     * *
     * @param bundle    携带的数据
     * *
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    fun gotoActivity(context: Context, gotoClass: Class<*>, bundle: Bundle) {
        val intent = Intent()
        intent.setClass(context, gotoClass)
        intent.putExtras(bundle)
        context.startActivity(intent)
    }

    /**
     * 跳转至指定activity,并关闭当前activity.

     * @param context   上下文
     * *
     * @param gotoClass 需要跳转的Activity界面
     * *
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    fun gotoActivityAndFinish(context: Context, gotoClass: Class<*>) {
        val intent = Intent()
        intent.setClass(context, gotoClass)
        context.startActivity(intent)
        (context as Activity).finish()
    }

    /**
     * 携带传递数据跳转至指定activity,并关闭当前activity.

     * @param context   上下文
     * *
     * @param gotoClass 需要跳转的页面
     * *
     * @param bundle    附带数据
     * *
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    fun gotoActivityAndFinish(context: Context, gotoClass: Class<*>, bundle: Bundle) {
        val intent = Intent()
        intent.setClass(context, gotoClass)
        intent.putExtras(bundle)
        context.startActivity(intent)
        (context as Activity).finish()
    }

    fun sendBroadcast(context: Context, intentAction: String) {
        val intent = Intent(intentAction)
        context.sendBroadcast(intent)
    }

    fun sendBroadcast(context: Context, intentAction: String, bundle: Bundle) {
        val intent = Intent(intentAction)
        intent.putExtras(bundle)
        context.sendBroadcast(intent)
    }

    fun startService(className: Class<*>) {
        val intent = Intent(context, className)//设置广播仅对本程序有效
        context?.startService(intent)
    }
}
