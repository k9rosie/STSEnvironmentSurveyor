package ie.k9ros.stsenvironmentsurveyor.game

import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen
import ie.k9ros.stsenvironmentsurveyor.utils.bounded
import ie.k9ros.stsenvironmentsurveyor.utils.toDouble
import kotlinx.serialization.Serializable

@Serializable
data class CardGrid(
    val minCardsToChoose: Double = -1.0,
    val isAnyNumberOfCards: Double = -1.0,
    val isForUpgrade: Double = -1.0,
    val isForTransform: Double = -1.0,
    val isForPurge: Double = -1.0,
    val isConfirming: Double = -1.0,
    val selectedCards: List<Card> = bounded(10, Card()),
    val cards: List<Card> = bounded(50, Card()),
    val blankSpace: Double = 1.0
)

fun getCardGrid(screen: GridCardSelectScreen?) = screen?.let {
    val numCardsField = GridCardSelectScreen::class.java.getDeclaredField("numCards")
    numCardsField.isAccessible = true
    val numCards = numCardsField.getInt(screen)
    CardGrid(
        numCards.toDouble(),
        toDouble(screen.anyNumber),
        toDouble(screen.forUpgrade),
        toDouble(screen.forTransform),
        toDouble(screen.forPurge),
        toDouble(screen.confirmScreenUp || screen.isJustForConfirming),
        boundedCardArray(screen.selectedCards, 10),
        boundedCardArray(screen.targetGroup?.group, 50),
        0.0
    )
} ?: CardGrid()
