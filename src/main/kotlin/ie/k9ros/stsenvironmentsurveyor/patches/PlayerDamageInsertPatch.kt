package ie.k9ros.stsenvironmentsurveyor.patches

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.characters.AbstractPlayer
import ie.k9ros.stsenvironmentsurveyor.Surveyor

@SpirePatch(clz = AbstractPlayer::class, method = "damage")
class PlayerDamageInsertPatch {
    companion object {
        @JvmStatic
        @SpireInsertPatch(loc = 1908, localvars = ["damageAmount"])
        fun Insert(__instance: AbstractPlayer, __info: DamageInfo, damageAmount: Int) {
            Surveyor.totalPlayerTakenDamage += damageAmount
        }
    }
}
