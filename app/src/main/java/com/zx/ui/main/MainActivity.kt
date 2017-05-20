package com.zx.ui.main

import android.app.Activity
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.youth.banner.Banner
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
import rx.Observable
import rx.schedulers.Schedulers

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.view_drawer)
    internal var viewDrawer: DrawerLayout? = null
    @BindView(R.id.banner)
    internal var bannerGuide: Banner? = null
    @BindView(R.id.view_content)
    internal var viewContent: CoordinatorLayout? = null
    @BindView(R.id.txt_search)
    internal var txtSearch: EditText? = null
    @BindView(R.id.nav_view)
    internal var navView: NavigationView? = null
    private var firstTime: Long = 0

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun initViewAndData() {
        ButterKnife.bind(this)

        // 主界面不可调用SwipeBack
        setSwipeBackEnable(false)
        initBGABanner()
        Observable.just(this).observeOn(Schedulers.newThread()).subscribe {
            navView?.setNavigationItemSelectedListener(this)
            RestrictUtils.getRestrictList()
            SQLiteUtils.getAllCardList()
        }
    }

    override fun onNavigationClick() {
        viewDrawer?.openDrawer(GravityCompat.START)
    }

    private fun initBGABanner() {
        val heightPx = (DisplayUtils.screenWidth - DisplayUtils.dip2px((16 * 2).toFloat())) * 29 / 68
        val marginPx = DisplayUtils.dip2px(16f)
        val params = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, heightPx)
        params.setMargins(marginPx, marginPx, marginPx, marginPx)
        bannerGuide?.layoutParams = params
        bannerGuide?.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
        bannerGuide?.setIndicatorGravity(BannerConfig.RIGHT)
        bannerGuide?.setImageLoader(BannerImageLoader())
        bannerGuide?.setImages(MapConst.GuideMap.entries.map { it.value })
        bannerGuide?.setOnBannerClickListener { position ->
            val querySql = SqlUtils.getPackQuerySql(MapConst.GuideMap.entries.map { it.key }[position - 1])
            ResultActivity.cardBeanList = SQLiteUtils.getCardList(querySql)
            IntentUtils.gotoActivity(this, ResultActivity::class.java)
        }
        bannerGuide?.start()
    }

    /**
     * 关键字搜索
     */
    @OnClick(R.id.fab_search)
    fun onSearch_Click() {
        DisplayUtils.hideKeyboard(this)
        val querySql = SqlUtils.getKeyQuerySql(txtSearch?.text.toString().trim { it <= ' ' })
        val cardBeanList = SQLiteUtils.getCardList(querySql)
        if (cardBeanList.isEmpty()) {
            showToast("没有查询到相关卡牌")
        } else {
            ResultActivity.cardBeanList = cardBeanList
            IntentUtils.gotoActivity(this, ResultActivity::class.java)
        }
    }

    override fun onBackPressed() {
        if (viewDrawer?.isDrawerOpen(GravityCompat.START) as Boolean) {
            viewDrawer?.closeDrawer(GravityCompat.START)
        } else {
            val lastTime = System.currentTimeMillis()
            val between = lastTime - firstTime
            if (between < 2000) {
                AppManager.getInstances().AppExit(this)
            } else {
                firstTime = lastTime
                showSnackBar(viewContent!!, "再按一次退出应用")
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
        viewDrawer?.closeDrawer(GravityCompat.START)
        return false
    }

    companion object {

        private val TAG = MainActivity::class.java.simpleName
    }
}
