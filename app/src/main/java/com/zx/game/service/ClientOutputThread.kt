package com.zx.game.service

import com.zx.uitls.LogUtils
import java.io.IOException
import java.io.OutputStream
import java.net.Socket
import java.util.*

/**
 * Created by Administrator on 2016/6/25.
 */
internal class ClientOutputThread(private val socket: Socket) : Thread() {
    private var os: OutputStream? = null
    private var isStart = true
    private var msg: ByteArray? = null

    init {
        try {
            os = socket.getOutputStream()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    fun setStart(isStart: Boolean) {
        this.isStart = isStart
    }

    // 这里处理跟服务器是一样的
    fun sendMsg(msg: ByteArray) {
        this.msg = msg
        synchronized(this) {
//            notifyAll()
        }
    }

    override fun run() {
        try {
            while (isStart) {
                if (msg != null) {
                    LogUtils.e(TAG, "Send：" + Arrays.toString(msg))
                    os?.write(msg!!)
                    os?.flush()
                    msg = null
                    synchronized(this) {
//                        wait()// 发送完消息后，线程进入等待状态
                    }
                }
            }
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                os?.close()
                socket.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }// 循环结束后，关闭输出流和socket
    }

    companion object {
        private val TAG = ClientOutputThread::class.java.simpleName
    }

}
