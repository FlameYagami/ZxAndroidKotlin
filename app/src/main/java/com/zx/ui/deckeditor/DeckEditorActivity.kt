package com.zx.ui.deckeditor

import android.app.Activity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import com.bumptech.glide.Glide
import com.michaelflisar.rxbus2.RxBusBuilder
import com.michaelflisar.rxbus2.rx.RxDisposableManager
import com.youth.banner.BannerConfig
import com.zx.R
import com.zx.bean.CardBean
import com.zx.bean.DeckBean
import com.zx.bean.DeckPreviewBean
import com.zx.config.Enum
import com.zx.config.MyApp.Companion.context
import com.zx.event.CardListEvent
import com.zx.game.DeckManager
import com.zx.game.utils.CardUtils
import com.zx.game.utils.DeckUtils
import com.zx.ui.advancedsearch.AdvancedSearchActivity
import com.zx.ui.base.BaseActivity
import com.zx.ui.detail.DetailActivity
import com.zx.uitls.BundleUtils
import com.zx.uitls.DisplayUtils
import com.zx.uitls.IntentUtils
import com.zx.uitls.PathManager
import com.zx.uitls.database.SQLiteUtils
import com.zx.uitls.database.SqlUtils
import com.zx.view.banner.BannerImageLoader
import com.zx.view.banner.BannerPageChangeListener
import kotlinx.android.synthetic.main.activity_deck_editor.*
import kotlinx.android.synthetic.main.include_detail.*
import kotlinx.android.synthetic.main.include_result_count.*
import kotlinx.android.synthetic.main.include_search.*
import kotlinx.android.synthetic.main.include_slv_count.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.sdk25.coroutines.onLongClick
import java.io.File

/**
 * Created by 八神火焰 on 2016/12/21.
 */
class DeckEditorActivity : BaseActivity() {

    internal var mPreviewCardAdapter = CardAdapter(this)
    internal var mIgDeckAdapter = DeckAdapter(this)
    internal var mUgDeckAdapter = DeckAdapter(this)
    internal var mEgDeckAdapter = DeckAdapter(this)
    internal var bannerPageChangeListener = BannerPageChangeListener()
    internal var mDeckManager: DeckManager = null!!

    companion object {
        private val TAG = DeckEditorActivity::class.java.simpleName
        var deckPreviewBean: DeckPreviewBean? = null
        var mSaveSucceed: String? = null
        var mSaveFailed: String? = null
    }

    override val layoutId: Int
        get() = R.layout.activity_deck_editor

    override fun initViewAndData() {
        mSaveSucceed = resources.getString(R.string.save_succeed)
        mSaveFailed = resources.getString(R.string.save_failed)
        viewAppBar.setNavigationClickListener { onBackPressed() }
        viewAppBar.setMenuClickListener(R.menu.item_deck_editor_menu, { _: MenuItem -> this::onMenuClick })

        val minHeightPx = (DisplayUtils.screenWidth - DisplayUtils.dip2px(32f)) / 10 * 7 / 5
        rv_ig.minimumHeight = minHeightPx
        rv_ug.minimumHeight = minHeightPx
        rv_ex.minimumHeight = minHeightPx
        rv_preview.minimumHeight = minHeightPx

        banner.setBannerStyle(BannerConfig.NUM_INDICATOR)
        banner.setImageLoader(BannerImageLoader())
        banner.setOnPageChangeListener(bannerPageChangeListener)

        rv_ig.layoutManager = GridLayoutManager(this, 10)
        rv_ig.adapter = mIgDeckAdapter
        mIgDeckAdapter.setOnItemClickListener { _: View, _: List<*>, _: Int -> this::updateDetailByDeck }
        mIgDeckAdapter.setOnItemLongClickListener { _: View, _: List<*>, _: Int -> this::removeCard }

        rv_ug.layoutManager = GridLayoutManager(this, 10)
        rv_ug.adapter = mUgDeckAdapter
        mUgDeckAdapter.setOnItemClickListener { _: View, _: List<*>, _: Int -> this::updateDetailByDeck }
        mUgDeckAdapter.setOnItemLongClickListener { _: View, _: List<*>, _: Int -> this::removeCard }

        rv_ex.layoutManager = GridLayoutManager(this, 10)
        rv_ex.adapter = mEgDeckAdapter
        mEgDeckAdapter.setOnItemClickListener { _: View, _: List<*>, _: Int -> this::updateDetailByDeck }
        mEgDeckAdapter.setOnItemLongClickListener { _: View, _: List<*>, _: Int -> this::removeCard }

        rv_preview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_preview.adapter = mPreviewCardAdapter
        mPreviewCardAdapter.setOnItemClickListener { _: View, _: List<*>, _: Int -> this::updateDetailByCard }
        mPreviewCardAdapter.setOnItemClickListener { _: View, _: List<*>, _: Int -> this::addCard }
        mDeckManager = DeckManager(deckPreviewBean?.deckName!!, deckPreviewBean?.numberExList)
        updateAllRecyclerView()
        updateStartAndLifeAndVoid(DeckUtils.getStartAndLifeAndVoidCount(mDeckManager))
        RxDisposableManager.addDisposable(this, RxBusBuilder.create(CardListEvent::class.java).subscribe({ this.updatePreview(it) }))

        img_pl.onClick { onPlayer_Click() }
        img_pl.onLongClick { onPlayer_LongClick() }
        fab_search.onClick { onSearch_Click() }
        banner.onClick { onBannerPreview_LongClick() }
        btn_deck_save.onClick { onDeckSave_Click() }
    }

    fun onPlayer_Click() {
        if (mDeckManager.playerList.isNotEmpty()) {
            val deckBean = mDeckManager.playerList[0]
            val cardBean = CardUtils.getCardBean(deckBean.numberEx)
            updateDetail(cardBean)
        }
    }

    fun onPlayer_LongClick(): Boolean {
        if (mDeckManager.playerList.isNotEmpty()) {
            val deckBean = mDeckManager.playerList[0]
            val cardBean = CardUtils.getCardBean(deckBean.numberEx)
            val areaType = CardUtils.getAreaType(cardBean)
            updateSingleRecyclerView(areaType, Enum.OperateType.Remove, -1)
        }
        return false
    }

    /**
     * 更新查询列表
     */
    private fun updatePreview(cardListEvent: CardListEvent) {
        tv_result_count.text = String.format("%s", cardListEvent.cardList.size)
        mPreviewCardAdapter.updateData(cardListEvent.cardList)
    }

    /**
     * 更新详细信息
     */
    fun updateDetailByDeck(view: View, data: List<*>, position: Int) {
        val deckBean = data[position] as DeckBean
        val cardBean = CardUtils.getCardBean(deckBean.numberEx)
        updateDetail(cardBean)
    }

    /**
     * 更新详细信息
     */
    fun updateDetailByCard(view: View, data: List<*>, position: Int) {
        val cardBean = data[position] as CardBean
        updateDetail(cardBean)
    }

    private fun updateDetail(cardBean: CardBean) {
        tv_cname.text = cardBean.cName
        tv_number.text = cardBean.number
        tv_power.text = cardBean.power
        tv_cost.text = cardBean.cost
        tv_race.text = cardBean.race
        tv_ability.text = cardBean.ability
        banner.setImages(CardUtils.getImagePathList(cardBean.image))
        banner.start()
    }

    private fun addCard(view: View, data: List<*>, position: Int) {
        val cardBean = data[position] as CardBean
        val numberEx = CardUtils.getNumberEx(cardBean.image, bannerPageChangeListener.currentIndex)
        val imagePath = PathManager.pictureCache + File.separator + numberEx + context?.getString(R.string.image_extension)
        val areaType = CardUtils.getAreaType(cardBean)
        val returnAreaType = mDeckManager.addCard(areaType, numberEx, imagePath)
        updateSingleRecyclerView(returnAreaType, Enum.OperateType.Add, position)
        updateStartAndLifeAndVoid(DeckUtils.getStartAndLifeAndVoidCount(mDeckManager))
    }

    fun removeCard(view: View, data: List<*>, position: Int) {
        val deckBean = data[position] as DeckBean
        val numberEx = deckBean.numberEx
        val areaType = CardUtils.getAreaType(numberEx)
        val returnAreaType = mDeckManager.deleteCard(areaType, numberEx)
        updateSingleRecyclerView(returnAreaType, Enum.OperateType.Remove, position)
        updateStartAndLifeAndVoid(DeckUtils.getStartAndLifeAndVoidCount(mDeckManager))
    }

    fun onSearch_Click() {
        DisplayUtils.hideKeyboard(this)
        val querySql = SqlUtils.getKeyQuerySql(txt_search.text.toString().trim { it <= ' ' })
        val cardList = SQLiteUtils.getCardList(querySql)
        tv_start_count.text = String.format("%s", cardList.size)
        mPreviewCardAdapter.updateData(cardList)
        if (cardList.isEmpty()) {
            showToast("没有查询到相关卡牌")
        }
    }

    fun onBannerPreview_LongClick(): Boolean {
        val number = tv_number.text.toString()
        DetailActivity.cardBean = CardUtils.getCardBean(number)
        IntentUtils.gotoActivity(this, DetailActivity::class.java)
        return false
    }

    fun onDeckSave_Click() {
        showToast(if (DeckUtils.saveDeck(mDeckManager)) mSaveSucceed!! else mSaveFailed!!)
    }

    private fun updateSingleRecyclerView(areaType: Enum.AreaType, operateType: Enum.OperateType, position: Int) {
        if (Enum.AreaType.None == areaType) {
            return
        }
        // 添加成功则更新该区域
        if (operateType == Enum.OperateType.Add) {
            if (areaType == Enum.AreaType.Player) {
                Glide.with(this).load(PathManager.pictureCache + File.separator + mDeckManager.playerList[0].numberEx + context?.getString(R.string.image_extension).toString())
                        .error(R.drawable.ic_unknown_picture).into(img_pl)
            } else if (areaType == Enum.AreaType.Ig) {
                mIgDeckAdapter.addData(mDeckManager.igList, mDeckManager.igList.size - 1)
            } else if (areaType == Enum.AreaType.Ug) {
                mUgDeckAdapter.addData(mDeckManager.ugList, mDeckManager.ugList.size - 1)
            } else if (areaType == Enum.AreaType.Ex) {
                mEgDeckAdapter.addData(mDeckManager.exList, mDeckManager.exList.size - 1)
            }
        } else {
            if (areaType == Enum.AreaType.Player) {
                img_pl.setImageBitmap(null)
            } else if (areaType == Enum.AreaType.Ig) {
                mIgDeckAdapter.removeData(mDeckManager.igList, position)
            } else if (areaType == Enum.AreaType.Ug) {
                mUgDeckAdapter.removeData(mDeckManager.ugList, position)
            } else if (areaType == Enum.AreaType.Ex) {
                mEgDeckAdapter.removeData(mDeckManager.exList, position)
            }
        }
    }

    private fun updateAllRecyclerView() {
        if (mDeckManager.playerList.isNotEmpty()) {
            Glide.with(this).load(PathManager.pictureCache + File.separator + mDeckManager.playerList[0].numberEx + context?.getString(R.string.image_extension)).error(null).into(img_pl)
        }
        mIgDeckAdapter.updateData(mDeckManager.igList)
        mUgDeckAdapter.updateData(mDeckManager.ugList)
        mEgDeckAdapter.updateData(mDeckManager.exList)
    }

    private fun updateStartAndLifeAndVoid(countStartAndLifeAndVoid: List<Int>) {
        val startCount = countStartAndLifeAndVoid[0]
        val lifeCount = countStartAndLifeAndVoid[1]
        val voidCount = countStartAndLifeAndVoid[2]
        tv_life_count.text = String.format("%s", startCount)
        tv_life_count.text = String.format("%s", lifeCount)
        tv_void_count.text = String.format("%s", voidCount)
    }

    fun onMenuClick(item: MenuItem) {
        when (item.itemId) {
            R.id.nav_advanced_search -> IntentUtils.gotoActivity(this@DeckEditorActivity, AdvancedSearchActivity::class.java,
                    BundleUtils.putString(Activity::class.java.simpleName, DeckEditorActivity::class.java.simpleName))
            R.id.nav_order_by_value -> {
                mDeckManager.orderDeck(Enum.DeckOrderType.Value)
                updateAllRecyclerView()
            }
            R.id.nav_order_by_random -> {
                mDeckManager.orderDeck(Enum.DeckOrderType.Random)
                updateAllRecyclerView()
            }
        }
    }
}
