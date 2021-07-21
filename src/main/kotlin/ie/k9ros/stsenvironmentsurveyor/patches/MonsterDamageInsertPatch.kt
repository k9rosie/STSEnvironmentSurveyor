package ie.k9ros.stsenvironmentsurveyor.patches

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.monsters.AbstractMonster
import ie.k9ros.stsenvironmentsurveyor.Surveyor

@SpirePatch(clz = AbstractMonster::class, method = "damage")
class MonsterDamageInsertPatch {
    companion object {
        @JvmStatic
        @SpireInsertPatch(loc = 769, localvars = ["damageAmount"])
        fun Insert(__instance: AbstractMonster, __info: DamageInfo, damageAmount: Int) {
            Surveyor.totalPlayerDealtDamage += damageAmount
        }
    }
}
