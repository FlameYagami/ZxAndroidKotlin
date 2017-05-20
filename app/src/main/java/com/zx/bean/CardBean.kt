package com.zx.bean

import java.io.Serializable

/**
 * Created by 时空管理局 on 2016/9/26.
 */
class CardBean : Serializable {
    var md5: String = ""
    var key: String = ""
    var abilityType: String = ""
    var type: String = ""
    var camp: String = ""
    var race: String = ""
        private set
    var sign: String = ""
        private set
    var rare: String = ""
        private set
    var pack: String = ""
        private set
    var restrict: String = ""
        private set
    var cName: String = ""
    var jName: String = ""
    var illust: String = ""
        private set
    var number: String = ""
        private set
    var cost: String = ""
        private set
    var power: String = ""
        private set
    var ability: String = ""
    var lines: String = ""
    var faq: String = ""
    var abilityDetail: String = ""
    var image: String = ""

    constructor(key: String, type: String, camp: String, race: String, sign: String, rare: String, pack: String, illust: String, restrict: String, cost: String, power: String, abilityType: String, abilityDetail: String) {
        this.key = key
        this.type = type
        this.camp = camp
        this.race = race
        this.sign = sign
        this.rare = rare
        this.pack = pack
        this.illust = illust
        this.cost = cost
        this.power = power
        this.restrict = restrict
        this.abilityType = abilityType
        this.abilityDetail = abilityDetail
    }

    /**
     * 全数据缓存构造方法
     */
    constructor(md5: String, type: String, race: String, camp: String, sign: String, rare: String, pack: String, restrict: String, cname: String, jname: String, illust: String, number: String, cost: String, power: String, ability: String, lines: String, faq: String, image: String) {
        this.md5 = md5
        this.type = type
        this.race = race
        this.camp = camp
        this.sign = sign
        this.rare = rare
        this.pack = pack
        this.restrict = restrict
        cName = cname
        jName = jname
        this.illust = illust
        this.number = number
        this.cost = cost
        this.power = power
        this.ability = ability
        this.lines = lines
        this.faq = faq
        this.image = image
    }

    constructor(number: String) {
        this.number = number
    }

    fun setType(type: String): CardBean {
        this.type = type
        return this
    }

    fun setCamp(camp: String): CardBean {
        this.camp = camp
        return this
    }
}
