package ie.k9ros.stsenvironmentsurveyor.game

import com.megacrit.cardcrawl.screens.select.HandCardSelectScreen
import ie.k9ros.stsenvironmentsurveyor.utils.bounded
import ie.k9ros.stsenvironmentsurveyor.utils.normalize
import ie.k9ros.stsenvironmentsurveyor.utils.toDouble
import kotlinx.serialization.Serializable

@Serializable
data class HandCardSelection(
    val maxCardsToSelect: Double = -1.0,
    val canPickNoCards: Double = -1.0,
    val hoveredCard: Card = Card(),
    val selectedCards: List<Card> = bounded(10, Card()),
    val blankSpace: Double = 1.0
)

fun getHardCardSelection(screen: HandCardSelectScreen?) = screen?.let {
    HandCardSelection(
        normalize(screen.numCardsToSelect),
        toDouble(screen.canPickZero),
        getCard(screen.hoveredCard),
        boundedCardArray(screen.selectedCards?.group, 10),
        0.0
    )
} ?: HandCardSelection()
