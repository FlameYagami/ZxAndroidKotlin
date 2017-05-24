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
import com.zx.uitls.BundleUtils
import com.zx.uitls.DisplayUtils
import com.zx.uitls.IntentUtils
import com.zx.uitls.database.SQLiteUtils
import com.zx.uitls.database.SqlUtils
import com.zx.view.banner.BannerImageLoader
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var firstTime: Long = 0

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun initViewAndData() {
        viewAppBar.setNavigationClickListener { view_drawer.openDrawer(GravityCompat.START) }
        nav_view.setNavigationItemSelectedListener(this)
        // 主界面不可调用SwipeBack
        setSwipeBackEnable(false)
        initBGABanner()
        Observable.just(this).observeOn(Schedulers.newThread()).subscribe {
            RestrictUtils.getRestrictList()
            SQLiteUtils.getAllCardList()
        }
    }

    private fun initBGABanner() {
        val heightPx = (DisplayUtils.screenWidth - DisplayUtils.dip2px((16 * 2).toFloat())) * 29 / 68
        val marginPx = DisplayUtils.dip2px(16f)
        val params = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, heightPx)
        params.setMargins(marginPx, marginPx, marginPx, marginPx)
        banner.layoutParams = params
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
        banner.setIndicatorGravity(BannerConfig.RIGHT)
        banner.setImageLoader(BannerImageLoader())
        banner.setImages(MapConst.GuideMap.entries.map { it.value })
        banner.setOnBannerClickListener { position ->
            val querySql = SqlUtils.getPackQuerySql(MapConst.GuideMap.entries.map { it.key }[position - 1])
            ResultActivity.cardBeanList = SQLiteUtils.getCardList(querySql).toMutableList()
            IntentUtils.gotoActivity(this, ResultActivity::class.java)
        }
        banner.start()
        fab_search.onClick { onSearch_Click() }
    }

    /**
     * 关键字搜索
     */
    fun onSearch_Click() {
        DisplayUtils.hideKeyboard(this)
        val querySql = SqlUtils.getKeyQuerySql(txt_search.text.toString().trim { it <= ' ' })
        val cardBeanList = SQLiteUtils.getCardList(querySql)
        if (cardBeanList.isEmpty()) {
            showToast("没有查询到相关卡牌")
        } else {
            ResultActivity.cardBeanList = cardBeanList.toMutableList()
            IntentUtils.gotoActivity(this, ResultActivity::class.java)
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
                showSnackBar(view_content, "再按一次退出应用")
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_advanced_search -> {
                IntentUtils.gotoActivity(this, AdvancedSearchActivity::class.java, BundleUtils.putString(Activity::class.java.simpleName, MainActivity::class.java.simpleName))
            }
            R.id.nav_deck_preview -> {
                IntentUtils.gotoActivity(this, DeckPreviewActivity::class.java)
            }
        //            case R.id.nav_duel: {
        //                IntentUtils.gotoActivity(this, VersusModeActivity.class);
        //                break;
        //            }
            R.id.nav_setting -> {
                IntentUtils.gotoActivity(this, SettingActivity::class.java)
            }
        }
        view_drawer.closeDrawer(GravityCompat.START)
        return false
    }

    companion object {

        private val TAG = MainActivity::class.java.simpleName
    }
}
