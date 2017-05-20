package com.zx.config

/**
 * Created by 八神火焰 on 2016/12/21.
 */

class Enum {
    enum class OperateType {
        Add,
        Remove
    }

    enum class AreaType {
        None,
        Player,
        Ig,
        Ug,
        Ex
    }

    enum class IgType {
        Life,
        Void,
        Normal
    }

    enum class UgType {
        Start,
        Normal
    }

    enum class DeckOrderType {
        Value,
        Random
    }
}
