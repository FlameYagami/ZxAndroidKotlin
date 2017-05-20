package com.zx.ui.deckeditor

import android.app.Activity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import butterknife.*
import com.bumptech.glide.Glide
import com.youth.banner.Banner
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
import com.zx.ui.base.BaseRecyclerViewListener
import com.zx.ui.detail.DetailActivity
import com.zx.uitls.*
import com.zx.uitls.database.SQLiteUtils
import com.zx.uitls.database.SqlUtils
import com.zx.view.banner.BannerImageLoader
import com.zx.view.banner.BannerPageChangeListener
import com.zx.view.widget.AppBarView
import java.io.File

/**
 * Created by 八神火焰 on 2016/12/21.
 */

class DeckEditorActivity : BaseActivity() {
    @BindView(R.id.rv_preview)
    internal var rvPreview: RecyclerView? = null
    @BindView(R.id.img_pl)
    internal var imgPl: ImageView? = null
    @BindView(R.id.rv_ig)
    internal var rvIg: RecyclerView? = null
    @BindView(R.id.rv_ug)
    internal var rvUg: RecyclerView? = null
    @BindView(R.id.rv_ex)
    internal var rvEx: RecyclerView? = null
    @BindView(R.id.tv_cname)
    internal var tvCname: TextView? = null
    @BindView(R.id.tv_number)
    internal var tvNumber: TextView? = null
    @BindView(R.id.tv_race)
    internal var tvRace: TextView? = null
    @BindView(R.id.tv_power)
    internal var tvPower: TextView? = null
    @BindView(R.id.tv_cost)
    internal var tvCost: TextView? = null
    @BindView(R.id.tv_ability)
    internal var tvAbility: TextView? = null
    @BindView(R.id.banner)
    internal var banner: Banner? = null
    @BindView(R.id.txt_search)
    internal var txtSearch: EditText? = null
    @BindString(R.string.save_succeed)
    internal var saveSucceed: String? = null
    @BindString(R.string.save_failed)
    internal var saveFailed: String? = null
    @BindView(R.id.tv_result_count)
    internal var tvResultCount: TextView? = null
    @BindView(R.id.tv_start_count)
    internal var tvStartCount: TextView? = null
    @BindView(R.id.tv_life_count)
    internal var tvLifeCount: TextView? = null
    @BindView(R.id.tv_void_count)
    internal var tvVoidCount: TextView? = null

    internal var mPreviewCardAdapter = CardAdapter(this)
    internal var mIgDeckAdapter = DeckAdapter(this)
    internal var mUgDeckAdapter = DeckAdapter(this)
    internal var mEgDeckAdapter = DeckAdapter(this)
    internal var bannerPageChangeListener = BannerPageChangeListener()
    internal var mDeckManager: DeckManager = null!!

    override val layoutId: Int
        get() = R.layout.activity_deck_editor

    override fun initViewAndData() {
        ButterKnife.bind(this)
        viewAppBar?.setMenuClickListener(R.menu.item_deck_editor_menu, MenuClickListener)

        val minHeightPx = (DisplayUtils.screenWidth - DisplayUtils.dip2px(32f)) / 10 * 7 / 5
        rvIg?.minimumHeight = minHeightPx
        rvUg?.minimumHeight = minHeightPx
        rvEx?.minimumHeight = minHeightPx
        rvPreview?.minimumHeight = minHeightPx

        banner?.setBannerStyle(BannerConfig.NUM_INDICATOR)
        banner?.setImageLoader(BannerImageLoader())
        banner?.setOnPageChangeListener(bannerPageChangeListener)

        rvIg?.layoutManager = GridLayoutManager(this, 10)
        rvIg?.adapter = mIgDeckAdapter
        mIgDeckAdapter.setOnItemClickListener(object : BaseRecyclerViewListener.OnItemClickListener {
            override fun onItemClick(view: View, data: List<*>, position: Int) {
                updateDetailByDeck(view, data, position)
            }
        })
        mIgDeckAdapter.setOnItemLongClickListener(object : BaseRecyclerViewListener.OnItemLongClickListener{
            override fun onItemLongClick(view: View, data: List<*>, position: Int) {
                removeCard(view, data, position)
            }
        })

        rvUg?.layoutManager = GridLayoutManager(this, 10)
        rvUg?.adapter = mUgDeckAdapter
        mUgDeckAdapter.setOnItemClickListener(object : BaseRecyclerViewListener.OnItemClickListener {
            override fun onItemClick(view: View, data: List<*>, position: Int) {
                updateDetailByDeck(view, data, position)
            }
        })
        mUgDeckAdapter.setOnItemLongClickListener(object : BaseRecyclerViewListener.OnItemLongClickListener{
            override fun onItemLongClick(view: View, data: List<*>, position: Int) {
                removeCard(view, data, position)
            }
        })

        rvEx?.layoutManager = GridLayoutManager(this, 10)
        rvEx?.adapter = mEgDeckAdapter
        mEgDeckAdapter.setOnItemClickListener(object : BaseRecyclerViewListener.OnItemClickListener {
            override fun onItemClick(view: View, data: List<*>, position: Int) {
                updateDetailByDeck(view, data, position)
            }
        })
        mEgDeckAdapter.setOnItemLongClickListener(object : BaseRecyclerViewListener.OnItemLongClickListener{
            override fun onItemLongClick(view: View, data: List<*>, position: Int) {
                removeCard(view, data, position)
            }
        })

        rvPreview?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvPreview?.adapter = mPreviewCardAdapter
        mPreviewCardAdapter.setOnItemClickListener(object : BaseRecyclerViewListener.OnItemClickListener {
            override fun onItemClick(view: View, data: List<*>, position: Int) {
                updateDetailByDeck(view, data, position)
            }
        })
        mPreviewCardAdapter.setOnItemClickListener(object : BaseRecyclerViewListener.OnItemClickListener {
            override fun onItemClick(view: View, data: List<*>, position: Int) {
                addCard(view, data, position)
            }
        })

        mDeckManager = DeckManager(deckPreviewBean?.deckName!!, deckPreviewBean?.numberExList)
        updateAllRecyclerView()
        updateStartAndLifeAndVoid(DeckUtils.getStartAndLifeAndVoidCount(mDeckManager))

        RxBus.instance.addSubscription(this, RxBus.instance.toObservable(CardListEvent::class.java).subscribe({ this.updatePreview(it) }))
    }

    override fun onNavigationClick() {
        onBackPressed()
    }

    @OnClick(R.id.img_pl)
    fun onPlayer_Click() {
        if (mDeckManager.playerList.isNotEmpty()) {
            val deckBean = mDeckManager.playerList[0]
            val cardBean = CardUtils.getCardBean(deckBean.numberEx)
            updateDetail(cardBean)
        }
    }

    @OnLongClick(R.id.img_pl)
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
        tvResultCount?.text = String.format("%s", cardListEvent.cardList.size)
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
        tvCname?.text = cardBean.cName
        tvNumber?.text = cardBean.number
        tvPower?.text = cardBean.power
        tvCost?.text = cardBean.cost
        tvRace?.text = cardBean.race
        tvAbility?.text = cardBean.ability
        banner?.setImages(CardUtils.getImagePathList(cardBean.image))
        banner?.start()
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

    @OnClick(R.id.fab_search)
    fun onSearch_Click() {
        DisplayUtils.hideKeyboard(this)
        val querySql = SqlUtils.getKeyQuerySql(txtSearch?.text.toString().trim { it <= ' ' })
        val cardList = SQLiteUtils.getCardList(querySql)
        tvResultCount?.text = String.format("%s", cardList.size)
        mPreviewCardAdapter.updateData(cardList)
        if (cardList.isEmpty()) {
            showToast("没有查询到相关卡牌")
        }
    }

    @OnLongClick(R.id.banner)
    fun onBannerPreview_LongClick(): Boolean {
        val number = tvNumber?.text.toString()
        DetailActivity.cardBean = CardUtils.getCardBean(number)
        IntentUtils.gotoActivity(this, DetailActivity::class.java)
        return false
    }

    @OnClick(R.id.btn_deck_save)
    fun onDeckSave_Click() {
        showToast(if (DeckUtils.saveDeck(mDeckManager)) saveSucceed!! else saveFailed!!)
    }

    private fun updateSingleRecyclerView(areaType: Enum.AreaType, operateType: Enum.OperateType, position: Int) {
        if (Enum.AreaType.None == areaType) {
            return
        }
        // 添加成功则更新该区域
        if (operateType == Enum.OperateType.Add) {
            if (areaType == Enum.AreaType.Player) {
                Glide.with(this).load(PathManager.pictureCache + File.separator + mDeckManager.playerList[0].numberEx + context?.getString(R.string.image_extension).toString())
                        .error(R.drawable.ic_unknown_picture).into(imgPl!!)
            } else if (areaType == Enum.AreaType.Ig) {
                mIgDeckAdapter.addData(mDeckManager.igList, mDeckManager.igList.size - 1)
            } else if (areaType == Enum.AreaType.Ug) {
                mUgDeckAdapter.addData(mDeckManager.ugList, mDeckManager.ugList.size - 1)
            } else if (areaType == Enum.AreaType.Ex) {
                mEgDeckAdapter.addData(mDeckManager.exList, mDeckManager.exList.size - 1)
            }
        } else {
            if (areaType == Enum.AreaType.Player) {
                imgPl?.setImageBitmap(null)
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
            Glide.with(this).load(PathManager.pictureCache + File.separator + mDeckManager.playerList[0].numberEx + context?.getString(R.string.image_extension)).error(null).into(imgPl!!)
        }
        mIgDeckAdapter.updateData(mDeckManager.igList)
        mUgDeckAdapter.updateData(mDeckManager.ugList)
        mEgDeckAdapter.updateData(mDeckManager.exList)
    }

    private fun updateStartAndLifeAndVoid(countStartAndLifeAndVoid: List<Int>) {
        val startCount = countStartAndLifeAndVoid[0]
        val lifeCount = countStartAndLifeAndVoid[1]
        val voidCount = countStartAndLifeAndVoid[2]
        tvStartCount?.text = String.format("%s", startCount)
        tvLifeCount?.text = String.format("%s", lifeCount)
        tvVoidCount?.text = String.format("%s", voidCount)
    }

    private val MenuClickListener = object : AppBarView.MenuClickListener {
        override fun onMenuClick(item: MenuItem) {
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

    companion object {

        private val TAG = DeckEditorActivity::class.java.simpleName

        var deckPreviewBean: DeckPreviewBean? = null
    }
}
