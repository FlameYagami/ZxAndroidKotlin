package com.dab.zx.bean

import java.util.*

/**
 * Created by 八神火焰 on 2016/12/21.
 */

class DeckPreviewBean(val deckName: String, val statusMain: String, val statusExtra: String, val playerPath: String, numberExList: List<String>) {
    var numberExList: List<String> = ArrayList()

    init {
        this.numberExList = numberExList
    }
}
