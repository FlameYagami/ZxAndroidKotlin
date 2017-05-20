package com.zx.game.service

import com.zx.config.MyApp
import com.zx.game.message.ModBusCreator

import java.net.InetSocketAddress
import java.net.Socket

/**
 * Created by Administrator on 2016/6/25.
 */
class ClientSocket
/**
 * 使用TCP协议,连接访问

 * @param ip   目标机器的IP
 * *
 * @param port 端口
 */
internal constructor(private val ip: String, private val port: Int, private val mConnectedListener: ClientSocket.ConnectedListener) : Thread() {
    var socket: Socket? = null
        private set
    private var client: Client? = null
    private var isStart = false

    interface ConnectedListener {
        fun isConnected(isConnected: Boolean)
    }

    override fun run() {
        try {
            //实例化一个Socket对象
            socket = Socket()
            //与对应的ip、端口进行连接,先要把桥建好
            socket?.connect(InetSocketAddress(ip, port), 3000)
            setClient()
        } catch (e: Exception) {
            e.printStackTrace()
            isStart = false
            mConnectedListener.isConnected(false)
        }

    }

    private fun setClient() {
        if (socket?.isConnected as Boolean) {
            println("打开TCP对应的输入/输出流监听")
            client = Client(socket as Socket)
            client?.start()
            isStart = true
            mConnectedListener.isConnected(true)
            if (socketHeartThread == null) {
                socketHeartThread = ClientHeartThread()
                socketHeartThread?.start()
            }
        }
    }

    //返回Socket状态
    fun isStart(): Boolean {
        return isStart
    }

    // 直接通过client停止读写消息
    internal fun setIsStart(isStart: Boolean) {
        if (this.isStart != isStart) {
            this.isStart = isStart
            if (client?.`in` != null) {
                client?.`in`?.setStart(isStart)
            }
            if (client?.out != null) {
                client?.out?.setStart(isStart)
            }
            if (!isStart) {
                socketHeartThread?.Cancel()
                socketHeartThread = null
            }
        }
    }

    fun sendMsg(msg: ByteArray) {
        if (null != client && null != client?.out) {
            client?.out?.sendMsg(msg)
        }
    }

    private inner class ClientHeartThread : Thread() {
        private var isRun: Boolean = false

        override fun run() {
            super.run()
            isRun = true
            while (isRun) {
                try {
                    MyApp.Client?.send(ModBusCreator.onHeart())
                    Thread.sleep(15000)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }

        internal fun Cancel() {
            isRun = false
        }
    }

    private inner class Client internal constructor(socket: Socket) {
        // 得到读消息线程
        val `in`: ClientInputThread?
        // 得到写消息线程
        val out: ClientOutputThread?

        init {
            //用这个监听输入流线程来接收信息
            `in` = ClientInputThread(socket)
            //以后就用这个监听输出流的线程来发送信息了
            out = ClientOutputThread(socket)
        }

        fun start() {
            `in`?.setStart(true)
            out?.setStart(true)
            `in`?.start()
            out?.start()
        }
    }

    companion object {

        private var socketHeartThread: ClientHeartThread? = null
    }
}
