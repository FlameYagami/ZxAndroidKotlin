package com.dab.zx.bean

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
    var sign: String = ""
    var rare: String = ""
    var pack: String = ""
    var restrict: String = ""
    var cname: String = ""
    var jname: String = ""
    var illust: String = ""
    var number: String = ""
    var cost: String = ""
    var power: String = ""
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
    constructor(md5: String, type: String, race: String, camp: String, sign: String, rare: String, pack: String, restrict: String, cname: String, jname: String, illust: String, number: String, cost: String, power: String, ability: String, lines: String, image: String) {
        this.md5 = md5
        this.type = type
        this.race = race
        this.camp = camp
        this.sign = sign
        this.rare = rare
        this.pack = pack
        this.restrict = restrict
        this.cname = cname
        this.jname = jname
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
}
