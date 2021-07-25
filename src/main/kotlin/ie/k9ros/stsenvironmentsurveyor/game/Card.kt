package ie.k9ros.stsenvironmentsurveyor.game

import com.megacrit.cardcrawl.cards.AbstractCard
import ie.k9ros.stsenvironmentsurveyor.utils.bounded
import ie.k9ros.stsenvironmentsurveyor.utils.hashed
import ie.k9ros.stsenvironmentsurveyor.utils.normalize
import ie.k9ros.stsenvironmentsurveyor.utils.toDouble
import kotlinx.serialization.Serializable

@Serializable
data class Card(
    val id: Double = -1.0,
    val price: Double = -1.0,
    val rarity: Double = -1.0,
    val color: Double = -1.0,
    val energyCost: Double = -2.0,
    val damage: Double = -1.0,
    val block: Double = -1.0,
    val heal: Double = -1.0,
    val draw: Double = -1.0,
    val discard: Double = -1.0,
    val damageTypeForTurn: Double = -1.0,
    val target: Double = -1.0,
    val isEnergyCostModified: Double = -1.0,
    val isRetained: Double = -1.0,
    val isInnate: Double = -1.0,
    val isLocked: Double = -1.0,
    val isUpgraded: Double = -1.0,
    val isSelected: Double = -1.0,
    val isExhaustable: Double = -1.0,
    val isReturnToHand: Double = -1.0,
    val isShuffledBackIntoDrawPile: Double = -1.0,
    val isEthereal: Double = -1.0,
    val isDamageModified: Double = -1.0,
    val isBlockModified: Double = -1.0,
    val isPurgeOnUse: Double = -1.0,
    val isExhaustOnUseOnce: Double = -1.0,
    val isExhaustOnFire: Double = -1.0,
    val isInAutoplay: Double = -1.0,
    val isInBottleFlame: Double = -1.0,
    val isInBottleLighting: Double = -1.0,
    val isInBottleTornado: Double = -1.0,
    val tags: List<Double> = bounded(5, -1.0),
    val blankSpace: Double = 1.0
)

fun getCard(card: AbstractCard?) = card?.let {
    Card(
        hashed(it.cardID),
        normalize(it.price),
        normalize(it.rarity?.ordinal ?: -1),
        normalize(it.color?.ordinal ?: -1),
        normalize(it.cost),
        normalize(it.damage),
        normalize(it.block),
        normalize(it.heal),
        normalize(it.draw),
        normalize(it.discard),
        normalize(it.damageTypeForTurn?.ordinal ?: -1),
        normalize(it.target?.ordinal ?: -1),
        toDouble(it.isCostModifiedForTurn),
        toDouble(it.retain),
        toDouble(it.isInnate),
        toDouble(it.isLocked),
        toDouble(it.upgraded),
        toDouble(it.isSelected),
        toDouble(it.exhaust),
        toDouble(it.returnToHand),
        toDouble(it.shuffleBackIntoDrawPile),
        toDouble(it.isEthereal),
        toDouble(it.isDamageModified),
        toDouble(it.isBlockModified),
        toDouble(it.purgeOnUse),
        toDouble(it.exhaustOnUseOnce),
        toDouble(it.exhaustOnFire),
        toDouble(it.isInAutoplay),
        toDouble(it.inBottleFlame),
        toDouble(it.inBottleLightning),
        toDouble(it.inBottleTornado),
        bounded(5, -1.0, card.tags) { i -> i.ordinal.toDouble() },
        0.0
    )
} ?: Card()

fun boundedCardArray(content: ArrayList<AbstractCard?>?, maxSize: Int) = bounded(maxSize, Card(), content) { getCard(it) }
