package com.zx.game.service

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder

import com.zx.config.MyApp
import com.zx.game.message.MessageManager
import com.zx.uitls.LogUtils


/**
 * 网络传输TCP服务
 */
class ClientService : Service() {

    private var mClientSocket: ClientSocket? = null
    private var mReceiver: BroadcastReceiver? = null
    private var mMessageManager: MessageManager? = null

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        initReceiver()
        LogUtils.d(TAG, "onCreate")
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        restartSocket()
        return Service.START_REDELIVER_INTENT
    }

    /**
     * 广播注册
     */
    private fun initReceiver() {
        mReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                when (intent.action) {
                    STOP_SERVICE -> stopSelf()
                    RESTART_SOCKET -> restartSocket()
                }
            }
        }
        val filter = IntentFilter()
        filter.addAction(STOP_SERVICE)
        filter.addAction(RESTART_SOCKET)
        registerReceiver(mReceiver, filter)
    }

    private fun closeSocket() {
        if (mClientSocket != null) {
            mClientSocket?.setIsStart(false)
            mClientSocket = null
            // 关闭Socket休眠0.5s重启
            try {
                Thread.sleep(500)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

        }
    }

    /**
     * 重启Socket
     */
    private fun restartSocket() {
        closeSocket()
        // 初始化
        mClientSocket = ClientSocket(serviceIP, servicePort , object : ClientSocket.ConnectedListener{
            override fun isConnected(isConnected: Boolean) {
                if (isConnected) {
                    mMessageManager = MessageManager(mClientSocket as ClientSocket)
                    mMessageManager?.start()
                    MyApp.Client?.initMessageManager(mMessageManager as MessageManager)
                }
            }
        })
        //正式启动线程
        mClientSocket?.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        closeSocket()
        unregisterReceiver(mReceiver)
        mMessageManager?.finish()
        LogUtils.d(TAG, "onDestroy")
    }

    companion object {
        private val TAG = ClientService::class.java.simpleName

        private val serviceIP = "191.191.16.167"
        private val servicePort = 8989

        val STOP_SERVICE = "STOP_SERVICE"
        val RESTART_SOCKET = "RESTART_SOCKET"
    }
}