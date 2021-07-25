package ie.k9ros.stsenvironmentsurveyor.game

import com.megacrit.cardcrawl.potions.AbstractPotion
import ie.k9ros.stsenvironmentsurveyor.utils.bounded
import ie.k9ros.stsenvironmentsurveyor.utils.hashed
import ie.k9ros.stsenvironmentsurveyor.utils.normalize
import ie.k9ros.stsenvironmentsurveyor.utils.toDouble
import kotlinx.serialization.Serializable

@Serializable
data class Potion(
    val id: Double = -1.0,
    val price: Double = -1.0,
    val rarity: Double = -1.0,
    val size: Double = -1.0,
    val isTargetRequired: Double = -1.0,
    val blankSpace: Double = 1.0
)

fun getPotion(potion: AbstractPotion?) = potion?.let {
    Potion(
        hashed(it.ID),
        normalize(it.price),
        normalize(it.rarity?.ordinal ?: -1),
        normalize(it.size?.ordinal ?: -1),
        toDouble(it.targetRequired),
        0.0
    )
} ?: Potion()

fun boundedPotionArray(content: ArrayList<AbstractPotion?>?, maxSize: Int = 5) = bounded(maxSize, Potion(), content) { getPotion(it) }
