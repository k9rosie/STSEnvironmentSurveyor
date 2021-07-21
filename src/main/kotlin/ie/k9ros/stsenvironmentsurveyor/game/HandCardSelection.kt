package ie.k9ros.stsenvironmentsurveyor.game

import com.megacrit.cardcrawl.screens.select.HandCardSelectScreen
import ie.k9ros.stsenvironmentsurveyor.utils.bounded
import ie.k9ros.stsenvironmentsurveyor.utils.toInt
import kotlinx.serialization.Serializable

@Serializable
data class HandCardSelection(
    val maxCardsToSelect: Int = -1,
    val canPickNoCards: Int = -1,
    val hoveredCard: Card = Card(),
    val selectedCards: List<Card> = bounded(10, Card()),
    val blankSpace: Int = 1
)

fun getHardCardSelection(screen: HandCardSelectScreen?) = screen?.let {
    HandCardSelection(
        screen.numCardsToSelect,
        toInt(screen.canPickZero),
        getCard(screen.hoveredCard),
        boundedCardArray(screen.selectedCards?.group, 10),
        0
    )
} ?: HandCardSelection()