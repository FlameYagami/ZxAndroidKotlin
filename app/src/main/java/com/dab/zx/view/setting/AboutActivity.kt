package com.dab.zx.view.setting

import com.dab.zx.R
import com.dab.zx.uitls.SystemUtils
import com.dab.zx.view.base.BaseActivity
import kotlinx.android.synthetic.main.activity_about.*

/**
 * Created by 八神火焰 on 2017/1/5.
 */
class AboutActivity : BaseActivity() {

    companion object {
        private val TAG = AboutActivity::class.java.simpleName
    }

    override val layoutId: Int
        get() = R.layout.activity_about

    override fun initViewAndData() {
        viewAppBar.setNavigationClickListener { onBackPressed() }
        msg_version.setValue(SystemUtils.versionName)
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
