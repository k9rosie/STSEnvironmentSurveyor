package ie.k9ros.stsenvironmentsurveyor.game

import com.megacrit.cardcrawl.relics.AbstractRelic
import ie.k9ros.stsenvironmentsurveyor.utils.bounded
import ie.k9ros.stsenvironmentsurveyor.utils.hashed
import ie.k9ros.stsenvironmentsurveyor.utils.normalize
import ie.k9ros.stsenvironmentsurveyor.utils.toDouble
import kotlinx.serialization.Serializable

@Serializable
data class Relic(
    val id: Double = -1.0,
    val price: Double = -1.0,
    val counter: Double = -1.0,
    val tier: Double = -1.0,
    val isObtained: Double = -1.0,
    val blankSpace: Double = .0,
)

fun getRelic(relic: AbstractRelic?) = relic?.let {
    Relic(
        hashed(it.relicId),
        normalize(it.price),
        normalize(it.counter),
        normalize(it.tier?.ordinal ?: -1),
        toDouble(it.isObtained),
        0.0
    )
} ?: Relic()

fun boundedRelicArray(content: ArrayList<AbstractRelic?>?, maxSize: Int = 30) = bounded(maxSize, Relic(), content) { getRelic(it) }
