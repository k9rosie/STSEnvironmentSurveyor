package ie.k9ros.stsenvironmentsurveyor.game

import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.ui.panels.EnergyPanel
import ie.k9ros.stsenvironmentsurveyor.utils.bounded
import ie.k9ros.stsenvironmentsurveyor.utils.normalize
import kotlinx.serialization.Serializable

@Serializable
data class Player(
    val character: Double = -1.0,
    val currentHealth: Double = -1.0,
    val currentBlock: Double = -1.0,
    val maxHealth: Double = -1.0,
    val currentEnergy: Double = -1.0,
    val maxEnergy: Double = -1.0,
    val lastDamageTaken: Double = -1.0,
    val powers: List<Power> = bounded(10, Power()),
    val relics: List<Relic> = bounded(30, Relic()),
    val potions: List<Potion> = bounded(5, Potion()),
    val deck: List<Card> = bounded(50, Card()),
    val drawPile: List<Card> = bounded(50, Card()),
    val exhaustPile: List<Card> = bounded(50, Card()),
    val discardPile: List<Card> = bounded(50, Card()),
    val hand: List<Card> = bounded(10, Card()),
    val blankSpace: Double = 1.0
)

fun getPlayer(player: AbstractPlayer?) = player?.let {
    Player(
        normalize(it.chosenClass?.ordinal ?: -1),
        normalize(it.currentHealth),
        normalize(it.currentBlock),
        normalize(it.maxHealth),
        normalize(EnergyPanel.totalCount),
        normalize(it.energy?.energy ?: -1),
        normalize(it.lastDamageTaken),
        boundedPowerArray(it.powers),
        boundedRelicArray(it.relics),
        boundedPotionArray(it.potions),
        boundedCardArray(it.masterDeck?.group),
        boundedCardArray(it.drawPile?.group),
        boundedCardArray(it.exhaustPile?.group),
        boundedCardArray(it.discardPile?.group),
        boundedCardArray(it.hand?.group, 10),
        0.0
    )
} ?: Player()
