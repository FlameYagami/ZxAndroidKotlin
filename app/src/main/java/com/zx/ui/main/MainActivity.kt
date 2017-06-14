package com.zx.ui.main

import android.app.Activity
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.FrameLayout
import com.youth.banner.BannerConfig
import com.zx.R
import com.zx.config.MapConst
import com.zx.game.utils.RestrictUtils
import com.zx.ui.advancedsearch.AdvancedSearchActivity
import com.zx.ui.base.BaseActivity
import com.zx.ui.deckpreview.DeckPreviewActivity
import com.zx.ui.result.ResultActivity
import com.zx.ui.setting.SettingActivity
import com.zx.uitls.AppManager
import com.zx.uitls.DisplayUtils
import com.zx.uitls.database.SQLiteUtils
import com.zx.uitls.database.SqlUtils
import com.zx.view.banner.BannerImageLoader
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var firstTime: Long = 0

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun initViewAndData() {
        viewAppBar.setNavigationClickListener { view_drawer.openDrawer(GravityCompat.START) }
        navView.setNavigationItemSelectedListener(this)
        // 主界面不可调用SwipeBack
        setSwipeBackEnable(false)
        initBannerPack()
        Observable.just(this).observeOn(Schedulers.io()).subscribe {
            RestrictUtils.getRestrictList()
            SQLiteUtils.getAllCardList()
        }
    }

    private fun initBannerPack() {
        val heightPx = (DisplayUtils.screenWidth - DisplayUtils.dip2px((16 * 2).toFloat())) * 29 / 68
        val marginPx = DisplayUtils.dip2px(16f)
        val params = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, heightPx)
        params.setMargins(marginPx, marginPx, marginPx, marginPx)
        bannerPack.layoutParams = params
        bannerPack.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
        bannerPack.setIndicatorGravity(BannerConfig.RIGHT)
        bannerPack.setImageLoader(BannerImageLoader())
        bannerPack.setImages(MapConst.GuideMap.entries.map { it.value })
        bannerPack.setOnBannerClickListener { position ->
            val querySql = SqlUtils.getPackQuerySql(MapConst.GuideMap.entries.map { it.key }[position - 1])
            ResultActivity.cardBeanList = SQLiteUtils.getCardList(querySql).toMutableList()
            startActivity<ResultActivity>()
        }
        bannerPack.start()
        fabSearch.onClick { onSearch_Click() }
    }

    /**
     * 关键字搜索
     */
    fun onSearch_Click() {
        DisplayUtils.hideKeyboard(this)
        val querySql = SqlUtils.getKeyQuerySql(txtSearch.text.toString().trim())
        val cardBeanList = SQLiteUtils.getCardList(querySql)
        if (cardBeanList.isEmpty()) {
            showToast(getString(R.string.main_card_not_found))
        } else {
            ResultActivity.cardBeanList = cardBeanList.toMutableList()
            startActivity<ResultActivity>()
        }
    }

    override fun onBackPressed() {
        if (view_drawer.isDrawerOpen(GravityCompat.START)) {
            view_drawer.closeDrawer(GravityCompat.START)
        } else {
            val lastTime = System.currentTimeMillis()
            val between = lastTime - firstTime
            if (between < 2000) {
                AppManager.instance().AppExit(this)
            } else {
                firstTime = lastTime
                showSnackBar(view_content, getString(R.string.main_exit_app))
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_advanced_search -> {
                startActivity<AdvancedSearchActivity>(Activity::class.java.simpleName to MainActivity::class.java.simpleName)
            }
            R.id.nav_deck_preview -> {
                startActivity<DeckPreviewActivity>()
            }
            R.id.nav_setting -> {
                startActivity<SettingActivity>()
            }
        }
        view_drawer.closeDrawer(GravityCompat.START)
        return false
    }
}
