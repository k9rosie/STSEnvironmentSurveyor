package ie.k9ros.stsenvironmentsurveyor.game

import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.ui.panels.EnergyPanel
import ie.k9ros.stsenvironmentsurveyor.utils.bounded
import kotlinx.serialization.Serializable

@Serializable
data class Player(
    val character: Int = -1,
    val currentHealth: Int = -1,
    val currentBlock: Int = -1,
    val maxHealth: Int = -1,
    val currentEnergy: Int = -1,
    val maxEnergy: Int = -1,
    val lastDamageTaken: Int = -1,
    val powers: List<Power> = bounded(10, Power()),
    val relics: List<Relic> = bounded(30, Relic()),
    val potions: List<Potion> = bounded(5, Potion()),
    val deck: List<Card> = bounded(50, Card()),
    val drawPile: List<Card> = bounded(50, Card()),
    val exhaustPile: List<Card> = bounded(50, Card()),
    val discardPile: List<Card> = bounded(50, Card()),
    val hand: List<Card> = bounded(10, Card()),
    val blankSpace: Int = 1
)

fun getPlayer(player: AbstractPlayer?) = player?.let {
    Player(
        it.chosenClass?.ordinal ?: -1,
        it.currentHealth,
        it.currentBlock,
        it.maxHealth,
        EnergyPanel.totalCount,
        it.energy?.energy ?: -1,
        it.lastDamageTaken,
        boundedPowerArray(it.powers),
        boundedRelicArray(it.relics),
        boundedPotionArray(it.potions),
        boundedCardArray(it.masterDeck?.group),
        boundedCardArray(it.drawPile?.group),
        boundedCardArray(it.exhaustPile?.group),
        boundedCardArray(it.discardPile?.group),
        boundedCardArray(it.hand?.group, 10),
        0
    )
} ?: Player()