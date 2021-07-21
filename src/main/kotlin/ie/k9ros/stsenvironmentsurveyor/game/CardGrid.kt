package ie.k9ros.stsenvironmentsurveyor.game

import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen
import ie.k9ros.stsenvironmentsurveyor.utils.bounded
import ie.k9ros.stsenvironmentsurveyor.utils.toInt
import kotlinx.serialization.Serializable

@Serializable
data class CardGrid(
    val minCardsToChoose: Int = -1,
    val isAnyNumberOfCards: Int = -1,
    val isForUpgrade: Int = -1,
    val isForTransform: Int = -1,
    val isForPurge: Int = -1,
    val isConfirming: Int = -1,
    val selectedCards: List<Card> = bounded(10, Card()),
    val cards: List<Card> = bounded(50, Card()),
    val blankSpace: Int = 1
)

fun getCardGrid(screen: GridCardSelectScreen?) = screen?.let {
    val numCardsField = GridCardSelectScreen::class.java.getDeclaredField("numCards")
    numCardsField.isAccessible = true
    val numCards = numCardsField.getInt(screen)
    CardGrid(
        numCards,
        toInt(screen.anyNumber),
        toInt(screen.forUpgrade),
        toInt(screen.forTransform),
        toInt(screen.forPurge),
        toInt(screen.confirmScreenUp || screen.isJustForConfirming),
        boundedCardArray(screen.selectedCards, 10),
        boundedCardArray(screen.targetGroup?.group, 50),
        0
    )
} ?: CardGrid()
