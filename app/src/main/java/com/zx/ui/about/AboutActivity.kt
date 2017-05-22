package com.zx.ui.about

import butterknife.ButterKnife
import com.zx.R
import com.zx.ui.base.BaseActivity
import com.zx.uitls.SystemUtils
import com.zx.view.widget.AppBarView
import kotlinx.android.synthetic.main.activity_about.*

/**
 * Created by 八神火焰 on 2017/1/5.
 */
class AboutActivity : BaseActivity() {

    override val layoutId: Int
        get() = R.layout.activity_about

    override fun initViewAndData() {
        ButterKnife.bind(this)
        viewAppBar.setNavigationClickListener(object : AppBarView.NavigationClickListener {
            override fun onNavigationClick() {
                onBackPressed()
            }
        })
        msg_version?.setValue(SystemUtils.versionName)
    }

    companion object {

        private val TAG = AboutActivity::class.java.simpleName
    }

    //    @OnClick(R.id.msg_update)
    //    public void onCheckUpdate(){
    //        RequestApi.checkUpdate().subscribe(mUpdateBean -> {
    //            LogUtils.e(TAG, JsonUtils.serializer(mUpdateBean));
    //        }, throwable -> {
    //            LogUtils.e(TAG,throwable.getMessage());
    //        });
    //    }
}
