package com.zx.view.widget

import android.content.Context
import android.support.v7.widget.Toolbar
import android.util.AttributeSet
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import butterknife.BindView
import butterknife.ButterKnife
import com.zx.R

/**
 * Created by 八神火焰 on 2017/1/6.
 */

class AppBarView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {
    @BindView(R.id.toolbar)
    internal var mToolbar: Toolbar? = null
    @BindView(R.id.view_shadowbar)
    var shadowBar: View? = null
        internal set
    @BindView(R.id.view_statusbar)
    var statusbar: View? = null
        internal set

    interface NavigationClickListener {
        fun onNavigationClick()
    }

    interface MenuClickListener {
        fun onMenuClick(item: MenuItem)
    }

    init {

        val view = View.inflate(context, R.layout.widget_appbar, null)
        addView(view)
        ButterKnife.bind(this, view)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.AppBarView)
        val titleText = typedArray.getString(R.styleable.AppBarView_title_text)
        val menuVisible = typedArray.getBoolean(R.styleable.AppBarView_menu_visible, false)
        val navigationResId = typedArray.getResourceId(R.styleable.AppBarView_navigation_src, R.mipmap.ic_nav_back)
        val menuResId = typedArray.getResourceId(R.styleable.AppBarView_menu_src, R.mipmap.ic_nav_menu)
        typedArray.recycle()

        mToolbar?.title = titleText
        mToolbar?.setNavigationIcon(navigationResId)
        if (menuVisible) {
            mToolbar?.overflowIcon = resources.getDrawable(menuResId)
        }
    }

    fun setNavigationClickListener(mNavigationClickListener: NavigationClickListener) {
        mToolbar?.setNavigationOnClickListener { mNavigationClickListener.onNavigationClick()}
    }

    fun setMenuClickListener(menuResId: Int, mMenuClickListener: MenuClickListener) {
        mToolbar?.inflateMenu(menuResId)
        mToolbar?.setOnMenuItemClickListener { item ->
            mMenuClickListener.onMenuClick(item)
            false
        }
    }

    fun setTitleText(titleText: String) {
        mToolbar?.title = titleText
    }
}
