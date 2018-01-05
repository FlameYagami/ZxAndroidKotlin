package com.dab.zx.uc.widget

import android.content.Context
import android.support.design.widget.AppBarLayout
import android.support.design.widget.AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
import android.support.design.widget.AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
import android.util.AttributeSet
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.dab.zx.R
import kotlinx.android.synthetic.main.widget_appbar.view.*

/**
 * Created by 八神火焰 on 2017/1/6.
 */

class AppBarView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private var onNavigationClick: (() -> Unit)? = null

    private var onMenuClickListener: ((item: MenuItem) -> Unit)? = null

    init {

        val view = View.inflate(context, R.layout.widget_appbar, null)
        addView(view)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.AppBarView)
        val titleText = typedArray.getString(R.styleable.AppBarView_title_text)
        val menuVisible = typedArray.getBoolean(R.styleable.AppBarView_menu_visible, false)
        val scrollFlags = typedArray.getBoolean(R.styleable.AppBarView_scrollFlags, false)
        val navigationResId = typedArray.getResourceId(R.styleable.AppBarView_navigation_src, R.mipmap.ic_nav_back)
        val menuResId = typedArray.getResourceId(R.styleable.AppBarView_menu_src, R.mipmap.ic_nav_menu)
        typedArray.recycle()

        toolbar.title = titleText
        toolbar.setNavigationIcon(navigationResId)
        if (scrollFlags) {
            val mParams = toolbar.layoutParams as AppBarLayout.LayoutParams
            mParams.scrollFlags = SCROLL_FLAG_SCROLL or SCROLL_FLAG_ENTER_ALWAYS
        }
        if (menuVisible) {
            toolbar.overflowIcon = resources.getDrawable(menuResId)
        }
        view.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    fun setNavigationClickListener(mListener: (() -> Unit)) {
        onNavigationClick = mListener
        toolbar.setNavigationOnClickListener { (onNavigationClick as () -> Unit).invoke() }
    }

    fun setMenuClickListener(menuResId: Int, mListener: (item: MenuItem) -> Unit) {
        onMenuClickListener = mListener
        toolbar.inflateMenu(menuResId)
        toolbar.setOnMenuItemClickListener { item ->
            onMenuClickListener?.invoke(item)
            false
        }
    }
}
