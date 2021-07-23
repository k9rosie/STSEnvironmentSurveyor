package ie.k9ros.stsenvironmentsurveyor.web

import ie.k9ros.stsenvironmentsurveyor.game.GameState
import kotlinx.serialization.Serializable

@Serializable
data class StateResponse(val score: Double, val gameState: GameState)
