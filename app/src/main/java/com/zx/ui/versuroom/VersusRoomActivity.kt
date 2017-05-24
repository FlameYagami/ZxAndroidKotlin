package com.zx.ui.versuroom

import com.michaelflisar.rxbus2.RxBusBuilder
import com.michaelflisar.rxbus2.rx.RxDisposableManager
import com.zx.R
import com.zx.config.MyApp
import com.zx.event.DuelistStateEvent
import com.zx.event.EnterGameEvent
import com.zx.event.LeaveGameEvent
import com.zx.event.StartGameEvent
import com.zx.game.Client
import com.zx.game.DeckManager
import com.zx.game.enums.PlayerType
import com.zx.game.message.ModBusCreator
import com.zx.ui.base.BaseExActivity
import com.zx.ui.versus.VersusActivity
import com.zx.uitls.IntentUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_versus_room.*
import java.util.concurrent.TimeUnit


/**
 * Created by 八神火焰 on 2017/1/16.
 */

class VersusRoomActivity : BaseExActivity() {
//    @BindString(R.string.host)
//    internal var host: String? = null
//    @BindString(R.string.guest)
//    internal var guest: String? = null
//    @BindString(R.string.ready)
//    internal var ready: String? = null
//    @BindString(R.string.cancel)
//    internal var notReady: String? = null
//
//    @BindViews(R.id.btn_ready_player0, R.id.btn_ready_player1)
//    internal var btnReady: Array<Button>? = null
//    @BindViews(R.id.tv_name_player0, R.id.tv_name_player1)
//    internal var tvName: Array<TextView>? = null
//    @BindViews(R.id.tv_player_type_0, R.id.tv_player_type_1)
//    internal var tvType: Array<TextView>? = null
//    @BindViews(R.id.tv_deck_name_player0, R.id.tv_deck_name_player1)
//    internal var tvDeckName: Array<TextView>? = null
//    @BindViews(R.id.img_deck_player0, R.id.img_deck_player1)
//    internal var imgDeck: Array<ImageView>? = null
//    @BindViews(R.id.view_player0, R.id.view_player1)
//    internal var viewPlayer: Array<RelativeLayout>? = null
//    @BindViews(R.id.view_hint_player0, R.id.view_hint_player1)
//    internal var viewHintPlayer: Array<RelativeLayout>? = null

    private var mUISubscriber: UISubscriber? = null
    private var ownerType: Byte = 0
    private var mDeckManager: DeckManager? = null

    override val layoutId: Int
        get() = R.layout.activity_versus_room

    override fun initViewAndData() {
        // 选手进入房间事件注册
        RxDisposableManager.addDisposable(this, RxBusBuilder.create(EnterGameEvent::class.java).subscribe({ this.onEnterRoom(it) }))
        // 选手状态事件注册
        RxDisposableManager.addDisposable(this, RxBusBuilder.create(DuelistStateEvent::class.java).subscribe({ this.onDuelistState(it) }))
        // 离开房间事件注册
        RxDisposableManager.addDisposable(this, RxBusBuilder.create(LeaveGameEvent::class.java).subscribe({ this.onLeaveRoom(it) }))
        // 开始游戏事件注册
        RxDisposableManager.addDisposable(this, RxBusBuilder.create(StartGameEvent::class.java).subscribe({ this.onStartGame(it) }))

        ownerType = MyApp.Client?.Player?.type as Byte
        tv_room_id.text = (MyApp.Client as Client).Room?.roomId
    }

    override fun onResume() {
        mUISubscriber = UISubscriber()
        super.onResume()
    }

    override fun onPause() {
        if (null != mUISubscriber) {
            mUISubscriber?.releaseSubscriber()
        }
        super.onPause()
    }


//    /**
//     * 卡组选择
//     */
//    @OnClick(R.id.img_deck_player0, R.id.img_deck_player1)
//    fun onDeckOwner_Click(view: View) {
//        if (view.id == imgDeck!![ownerType.toInt()].id) {
//            DialogDeckPreview(this) { dialog, bean ->
//                Glide.with(this).load(bean.playerPath).into(imgDeck!![ownerType.toInt()])
//                tvDeckName!![ownerType.toInt()].text = bean.deckName
//                mDeckManager = DeckManager(bean.deckName, bean.numberExList)
//            }.show()
//        }
//    }
//
//    /**
//     * 准备状态
//     */
//    @OnClick(R.id.btn_ready_player0, R.id.btn_ready_player1)
//    fun onReady_Click(view: View) {
//        if (view.id == btnReady!![ownerType.toInt()].id) {
//            if (TextUtils.isEmpty(tvDeckName!![ownerType.toInt()].text)) {
//                showToast("请选择卡组")
//            } else {
//                showDialog("")
//                MyApp.Client?.send(ModBusCreator.onPlayerState((MyApp.Client as Client).Player as Player))
//            }
//        }
//    }

    /**
     * 进入房间事件
     */
    private fun onEnterRoom(mEnterGameEvent: EnterGameEvent) {
        showToast(mEnterGameEvent.player.name + "进入了房间")
    }

    /**
     * 离开房间事件
     */
    private fun onLeaveRoom(mLeaveGameEvent: LeaveGameEvent) {
        if (mLeaveGameEvent.ownerType == mLeaveGameEvent.player.type) {
            super.onBackPressed()
        } else {
            if (0 == mLeaveGameEvent.player.type.toInt()) {
                showToast(mLeaveGameEvent.player.name + "撤销了房间")
            } else {
                showToast(mLeaveGameEvent.player.name + "离开了房间")
            }
        }
    }

    /**
     * 选手状态改变
     */
    private fun onDuelistState(mDuelistStateEvent: DuelistStateEvent) {
        hideDialog()
        if (2 == MyApp.Client?.Game?.duelists?.count { it?.isReady as Boolean } && PlayerType.Host == ownerType) {
            showDialog("")
            MyApp.Client?.send(ModBusCreator.onStartGame(mDeckManager!!))
        }
    }

    /**
     * 开始游戏
     */
    private fun onStartGame(mStartGameEvent: StartGameEvent) {
        hideDialog()
        IntentUtils.gotoActivity(this, VersusActivity::class.java)
    }

    /**
     * 更新界面
     */
    private fun updateUI() {
//        if (null != MyApp.Client?.Game) {
//            val players = (MyApp.Client as Client).Game?.duelists
//            for (i in 0..1) {
//                if (null == players?.get(i)) {
//                    viewPlayer!![i].visibility = View.GONE
//                    viewHintPlayer!![i].visibility = View.VISIBLE
//                    return
//                }
//                viewPlayer!![i].visibility = View.VISIBLE
//                viewHintPlayer!![i].visibility = View.GONE
//                tvType!![i].text = if (players[i]?.type == PlayerType.Host) host else guest
//                tvName!![i].text = players[i]?.name
//                btnReady!![i].text = if (players[i]?.isReady as Boolean) notReady else ready
//                btnReady!![i].isEnabled = players[i]?.type == ownerType
//            }
//        }
    }

    /**
     * 界面轮询
     */
    private inner class UISubscriber internal constructor() {
        internal var stopMePlease: Disposable? = null

        init {
            stopMePlease = Observable.interval(100, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe { updateUI() }
        }

        internal fun releaseSubscriber() {
            stopMePlease?.dispose()
            stopMePlease = null
        }
    }
}
