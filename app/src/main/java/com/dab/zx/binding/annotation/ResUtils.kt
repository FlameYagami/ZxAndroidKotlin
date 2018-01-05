package com.dab.zx.binding.annotation

import com.dab.zx.BR
import com.dab.zx.binding.ItemView

object ResUtils {
    const val NO_BR = -1

    fun getItemView(res: HeaderResHolder?): ItemView? {
        return res?.let {
            ItemView.of(
                    if (it.br != NO_BR) it.br else BR.item,
                    it.layout
            )
        }
    }

    fun getItemView(subitemRes: SubitemResHolder?): ItemView? {
        return subitemRes?.let {
            ItemView.of(
                    if (it.br != NO_BR) it.br else BR.item,
                    it.layout
            )
        }
    }
}
