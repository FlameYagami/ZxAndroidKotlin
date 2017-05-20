package com.zx.uitls

import android.util.Log

/**
 * Log 日志工具类

 * @author weijiang.Zeng
 */
object LogUtils {

    //当前Debug模式
    var isDebug = true

    fun e(tag: String, text: String) {
        if (isDebug) {
            Log.e(tag, text)
        }
    }

    fun d(tag: String, text: String) {
        if (isDebug) {
            Log.d(tag, text)
        }
    }

    fun i(tag: String, text: String) {
        if (isDebug) {
            Log.i(tag, text)
        }
    }

    fun w(tag: String, text: String) {
        if (isDebug) {
            Log.w(tag, text)
        }
    }

    fun v(tag: String, text: String) {
        if (isDebug) {
            Log.v(tag, text)
        }
    }

    private val LOG_FORMAT = "%1\$s\n%2\$s"

    fun d(tag: String, vararg args: Any) {
        log(Log.DEBUG, null, tag, *args)
    }

    fun i(tag: String, vararg args: Any) {
        log(Log.INFO, null, tag, *args)
    }

    fun w(tag: String, vararg args: Any) {
        log(Log.WARN, null, tag, *args)
    }

    fun e(ex: Throwable) {
        log(Log.ERROR, ex, null!!)
    }

    fun e(tag: String, vararg args: Any) {
        log(Log.ERROR, null, tag, *args)
    }

    fun e(ex: Throwable, tag: String, vararg args: Any) {
        log(Log.ERROR, ex, tag, *args)
    }

    private fun log(priority: Int, ex: Throwable?, tag: String, vararg args: Any) {

        if (!LogUtils.isDebug) {
            return
        }

        var log = ""
        if (ex == null) {
            if (args.isNotEmpty()) {
                for (obj in args) {
                    log += obj.toString()
                }
            }
        } else {
            val logMessage = ex.message
            val logBody = Log.getStackTraceString(ex)
            log = String.format(LOG_FORMAT, logMessage, logBody)
        }
        Log.println(priority, tag, log)
    }

}
