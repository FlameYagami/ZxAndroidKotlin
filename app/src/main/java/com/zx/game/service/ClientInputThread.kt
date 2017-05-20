package com.zx.game.service

import com.zx.config.MyApp
import com.zx.uitls.LogUtils
import java.io.IOException
import java.io.InputStream
import java.net.Socket
import java.util.*

/**
 * Created by Administrator on 2016/6/25.
 */
internal class ClientInputThread(private val socket: Socket) : Thread() {
    private var isStart = true
    private var `is`: InputStream? = null
    private var clientInputCache: ClientInputCache? = null

    init {
        try {
            `is` = socket.getInputStream()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    fun setStart(isStart: Boolean) {
        this.isStart = isStart
    }

    override fun run() {
        try {
            clientInputCache = ClientInputCache (object : ClientInputCache.OnReadListener{
                override fun read(bytes: ByteArray) {
                    MyApp.Client?.receive(bytes)
                    LogUtils.e(TAG, "Read->完整数据包" + Arrays.toString(bytes))
                }
            })

            while (isStart) {
                //读取信息,如果没信息将会阻塞线程
                val data = ByteArray(1024)
                val len = `is`?.read(data)
                if (-1 != len && isStart) {
                    val bytes = ByteArray(len as Int)
                    System.arraycopy(data, 0, bytes, 0, len)
                    clientInputCache?.write(bytes)
                    LogUtils.e(TAG, "Read->可能存在粘包的数据包" + Arrays.toString(bytes))
                }
            }
        } catch (e: Exception) {
            LogUtils.e(TAG, "Read线程抛异常")
            e.printStackTrace()
        } finally {
            try {
                `is`?.close()
                socket.close()
                clientInputCache?.onDestroy()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

    companion object {
        private val TAG = ClientInputThread::class.java.simpleName
    }
}