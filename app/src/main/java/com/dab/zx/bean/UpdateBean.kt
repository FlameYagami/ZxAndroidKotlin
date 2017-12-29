package com.dab.zx.bean

import java.io.Serializable

class UpdateBean : Serializable {
    val versionname: String? = null
    val url: String? = null
    val info: String? = null
    val versioncode: Int
    val size: Long

    init {
        versioncode = -1
        size = -1
    }
}
