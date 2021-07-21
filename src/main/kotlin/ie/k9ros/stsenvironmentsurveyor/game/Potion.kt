package ie.k9ros.stsenvironmentsurveyor.game

import com.megacrit.cardcrawl.potions.AbstractPotion
import ie.k9ros.stsenvironmentsurveyor.utils.bounded
import ie.k9ros.stsenvironmentsurveyor.utils.hashed
import ie.k9ros.stsenvironmentsurveyor.utils.toInt
import kotlinx.serialization.Serializable

@Serializable
data class Potion(
    val id: Int = -1,
    val price: Int = -1,
    val rarity: Int = -1,
    val size: Int = -1,
    val isTargetRequired: Int = -1,
    val blankSpace: Int = 1
)

fun getPotion(potion: AbstractPotion?) = potion?.let {
    Potion(
        hashed(it.ID),
        it.price,
        it.rarity?.ordinal ?: -1,
        it.size?.ordinal ?: -1,
        toInt(it.targetRequired),
        0
    )
} ?: Potion()

fun boundedPotionArray(content: ArrayList<AbstractPotion?>?, maxSize: Int = 5) = bounded(maxSize, Potion(), content) { getPotion(it) }
