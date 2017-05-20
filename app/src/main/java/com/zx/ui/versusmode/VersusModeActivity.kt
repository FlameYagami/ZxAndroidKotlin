package com.zx.ui.versusmode

import android.view.View
import butterknife.ButterKnife
import butterknife.OnClick
import com.zx.R
import com.zx.config.MyApp
import com.zx.event.JoinGameEvent
import com.zx.game.enums.PlayerType
import com.zx.ui.base.BaseActivity
import com.zx.ui.versuroom.VersusRoomActivity
import com.zx.uitls.IntentUtils
import com.zx.uitls.RxBus
import com.zx.view.dialog.DialogEditText
import com.zx.view.dialog.DialogVersusPersonal
import java.util.*

/**
 * Created by 八神火焰 on 2017/2/8.
 */

class VersusModeActivity : BaseActivity() {
    internal var mDialogVersusPersonal: DialogVersusPersonal = null!!
    internal var mDialogJoinRoom: DialogEditText

    override val layoutId: Int
        get() = R.layout.activity_versus_mode

    override fun initViewAndData() {
        ButterKnife.bind(this)
        MyApp.Client?.initPlayer(Random().nextInt().toString())
        MyApp.Client?.start()

        RxBus.instance.addSubscription(this, RxBus.instance.toObservable(JoinGameEvent::class.java).subscribe({ this.onJoinRoom(it) }))
    }

    override fun onNavigationClick() {
        onBackPressed()
    }

    @OnClick(R.id.view_ladder_tournament, R.id.view_versus_freedom, R.id.view_versus_personal)
    fun onMode_Click(view: View) {
        when (view.id) {
            R.id.view_ladder_tournament -> {
            }
            R.id.view_versus_freedom -> {
            }
            R.id.view_versus_personal -> onVersusPersonal()
        }
    }

    private fun onJoinRoom(mJoinGameEvent: JoinGameEvent) {
        hideDialog()
        if (mJoinGameEvent.playerType == PlayerType.Host) {
            mDialogVersusPersonal.dismiss()
            if (mJoinGameEvent.isSucceed) {
                IntentUtils.gotoActivity(this, VersusRoomActivity::class.java)
            } else {
                showToast("服务器房间队列已满,请稍后再试")
            }
        } else {
            mDialogJoinRoom.dismiss()
            if (mJoinGameEvent.isSucceed) {
                IntentUtils.gotoActivity(this, VersusRoomActivity::class.java)
            } else {
                showToast("该房间不存在")
            }
        }
    }

    /**
     * 私人对战事件
     */
    private fun onVersusPersonal() {
//        mDialogVersusPersonal = DialogVersusPersonal(this) { dialog, type ->
//            if (type == DialogVersusPersonal.ButtonType.JoinRoom) {
//                dialog.dismiss()
//                onJoinRoom()
//            } else if (type == DialogVersusPersonal.ButtonType.CreateRoom) {
//                showDialog("")
//                MyApp.Client.send(ModBusCreator.onCreateRoom(MyApp.Client.Player))
//            }
//        }
//        mDialogVersusPersonal.show()
    }

    /**
     * 加入房间事件
     */
    private fun onJoinRoom() {
//        mDialogJoinRoom = DialogEditText(this, getString(R.string.join_room), "", "请输入房间号") { dialog, content ->
//            MyApp.Client.send(ModBusCreator.onJoinRoom(content, MyApp.Client.Player))
//            showDialog("")
//        }
//        mDialogJoinRoom.show()
    }

    override fun onDestroy() {
        MyApp.Client?.finish()
        super.onDestroy()
    }

    companion object {

        private val TAG = VersusModeActivity::class.java.simpleName
    }
}
