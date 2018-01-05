package com.dab.zx.binding.annotation

import android.support.annotation.LayoutRes

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class HeaderResHolder(@LayoutRes val layout: Int, val br: Int = ResUtils.NO_BR)
