package com.zx.bean

import com.zx.config.MapConst
import java.util.*

/**
 * Created by 八神火焰 on 2016/12/16.
 */

object AbilityTypeBean {
    var abilityTypeMap = LinkedHashMap<String, Boolean>()

    init {
        for ((key) in MapConst.AbilityTypeMap) {
            abilityTypeMap.put(key, false)
        }
    }

    fun initAbilityTypeMap() {
        for ((key) in abilityTypeMap) {
            abilityTypeMap.put(key, false)
        }
    }
}
