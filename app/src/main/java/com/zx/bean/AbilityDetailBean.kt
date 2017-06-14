package com.zx.bean

import com.zx.config.MapConst
import java.util.*

/**
 * Created by 八神火焰 on 2016/12/16.
 */

object AbilityDetailBean {
    var abilityDetailMap = LinkedHashMap<String, Boolean>()

    init {
        for (key in MapConst.AbilityDetailMap.keys) {
            abilityDetailMap.put(key, false)
        }
    }

    fun initAbilityDetailMap() {
        for (key in abilityDetailMap.keys) {
            abilityDetailMap.put(key, false)
        }
    }
}
