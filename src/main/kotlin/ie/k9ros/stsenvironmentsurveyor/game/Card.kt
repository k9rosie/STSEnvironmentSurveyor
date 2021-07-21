package ie.k9ros.stsenvironmentsurveyor.game

import com.megacrit.cardcrawl.cards.AbstractCard
import ie.k9ros.stsenvironmentsurveyor.utils.bounded
import ie.k9ros.stsenvironmentsurveyor.utils.hashed
import ie.k9ros.stsenvironmentsurveyor.utils.toInt
import kotlinx.serialization.Serializable

@Serializable
data class Card(
    val id: Int = -1,
    val price: Int = -1,
    val rarity: Int = -1,
    val color: Int = -1,
    val energyCost: Int = -2,
    val damage: Int = -1,
    val block: Int = -1,
    val heal: Int = -1,
    val draw: Int = -1,
    val discard: Int = -1,
    val damageTypeForTurn: Int = -1,
    val target: Int = -1,
    val isEnergyCostModified: Int = -1,
    val isRetained: Int = -1,
    val isInnate: Int = -1,
    val isLocked: Int = -1,
    val isUpgraded: Int = -1,
    val isSelected: Int = -1,
    val isExhaustable: Int = -1,
    val isReturnToHand: Int = -1,
    val isShuffledBackIntoDrawPile: Int = -1,
    val isEthereal: Int = -1,
    val isDamageModified: Int = -1,
    val isBlockModified: Int = -1,
    val isPurgeOnUse: Int = -1,
    val isExhaustOnUseOnce: Int = -1,
    val isExhaustOnFire: Int = -1,
    val isInAutoplay: Int = -1,
    val isInBottleFlame: Int = -1,
    val isInBottleLighting: Int = -1,
    val isInBottleTornado: Int = -1,
    val tags: List<Int> = bounded(5, -1),
    val blankSpace: Int = 1
)

fun getCard(card: AbstractCard?) = card?.let {
    Card(
        hashed(it.cardID),
        it.price,
        it.rarity?.ordinal ?: -1,
        it.color?.ordinal ?: -1,
        it.cost,
        it.damage,
        it.block,
        it.heal,
        it.draw,
        it.discard,
        it.damageTypeForTurn?.ordinal ?: -1,
        it.target?.ordinal ?: -1,
        toInt(it.isCostModifiedForTurn),
        toInt(it.retain),
        toInt(it.isInnate),
        toInt(it.isLocked),
        toInt(it.upgraded),
        toInt(it.isSelected),
        toInt(it.exhaust),
        toInt(it.returnToHand),
        toInt(it.shuffleBackIntoDrawPile),
        toInt(it.isEthereal),
        toInt(it.isDamageModified),
        toInt(it.isBlockModified),
        toInt(it.purgeOnUse),
        toInt(it.exhaustOnUseOnce),
        toInt(it.exhaustOnFire),
        toInt(it.isInAutoplay),
        toInt(it.inBottleFlame),
        toInt(it.inBottleLightning),
        toInt(it.inBottleTornado),
        bounded(5, -1, card.tags) { i -> i.ordinal },
        0
    )
} ?: Card()

fun boundedCardArray(content: ArrayList<AbstractCard?>?, maxSize: Int = 50) = bounded(maxSize, Card(), content) { getCard(it) }
