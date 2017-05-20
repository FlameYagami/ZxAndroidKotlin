package com.zx.bean

import com.zx.config.MapConst
import java.util.*

/**
 * Created by 八神火焰 on 2016/12/16.
 */

object AbilityDetailBean {
    var abilityDetailMap = LinkedHashMap<String, Boolean>()

    init {
        for ((key) in MapConst.AbilityDetailMap) {
            abilityDetailMap.put(key, false)
        }
    }

    fun initAbilityDetailMap() {
        for ((key) in abilityDetailMap) {
            abilityDetailMap.put(key, false)
        }
    }
}
