package com.dab.zx.model

import android.databinding.ObservableField

/**
 * Created by 八神火焰 on 2018/1/18.
 */
class AdvancedSearchModel {
    var key: ObservableField<String> = ObservableField("")
    var cost:ObservableField<String> = ObservableField("")
    var power:ObservableField<String> = ObservableField("")
    val type :ObservableField<String> = ObservableField("")
    val camp :ObservableField<String> = ObservableField("")
    val race :ObservableField<String> = ObservableField("")
    val sign :ObservableField<String> = ObservableField("")
    val rare :ObservableField<String> = ObservableField("")
    val pack :ObservableField<String> = ObservableField("")
    val illust :ObservableField<String> = ObservableField("")
    val restrict :ObservableField<String> = ObservableField("")
}