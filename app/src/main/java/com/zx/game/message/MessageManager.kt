package com.zx.game.message

import com.zx.game.service.ClientSocket
import com.zx.uitls.LogUtils
import com.zx.uitls.Md5Utils
import rx.Observable
import rx.Subscription
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by 八神火焰 on 2017/2/10.
 */

class MessageManager(private val mClientSocket: ClientSocket) {

    private val mModBusAnalyser: ModBusAnalyser = ModBusAnalyser()
    private var mTickSubscriber: TickSubscriber? = null
    private val sendQueue = LinkedList<ByteArray>()
    private val receiveQueue = LinkedList<ServicePacket>()

    fun start() {
        mTickSubscriber = TickSubscriber()
    }

    fun finish() {
        mTickSubscriber?.releaseSubscriber()
    }

    fun sendMessage(clientPacket: ClientPacket) {
        // 添加Md5验证码
        clientPacket.write(Md5Utils.calculate(clientPacket.bytes))
        sendQueue.add(clientPacket.bytes)
    }

    fun receiveMessage(bytes: ByteArray) {
        if (!Md5Utils.check(bytes)) {
            LogUtils.e(TAG, "Md5校验错误->", Arrays.toString(bytes))
            return
        }
        // 移除Md5验证码
        val removeMd5 = Md5Utils.removeMd5(bytes)
        receiveQueue.add(ServicePacket(removeMd5))
    }

    private fun tick() {
        //        if (checkDisconnected()) {
        //            networkSend();
        //            networkReceive();
        //        }
        networkSend()
        networkReceive()
    }

    private fun checkDisconnected(): Boolean {
        try {
            mClientSocket.socket?.sendUrgentData(0xFF)
            return true
        } catch (e: IOException) {
            e.printStackTrace()
            return false
        }

    }

    private fun networkSend() {
        if (sendQueue.size > 0) {
            mClientSocket.sendMsg(sendQueue.peek())
            sendQueue.remove()
        }
    }

    private fun networkReceive() {
        if (receiveQueue.size > 0) {
            mModBusAnalyser.analysis(receiveQueue.peek())
            receiveQueue.remove()
        }
    }

    private inner class TickSubscriber internal constructor() {
        internal var stopMePlease: Subscription? = null

        init {
            stopMePlease = Observable.interval(100, TimeUnit.MILLISECONDS).subscribe { aLong -> tick() }
        }

        internal fun releaseSubscriber() {
            stopMePlease?.unsubscribe()
            stopMePlease = null
        }
    }

    companion object {
        private val TAG = MessageManager::class.java.simpleName
    }
}
