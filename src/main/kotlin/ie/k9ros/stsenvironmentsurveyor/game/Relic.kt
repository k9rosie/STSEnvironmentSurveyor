package ie.k9ros.stsenvironmentsurveyor.game

import com.megacrit.cardcrawl.relics.AbstractRelic
import ie.k9ros.stsenvironmentsurveyor.utils.bounded
import ie.k9ros.stsenvironmentsurveyor.utils.hashed
import ie.k9ros.stsenvironmentsurveyor.utils.toInt
import kotlinx.serialization.Serializable

@Serializable
data class Relic(
    val id: Int = -1,
    val price: Int = -1,
    val counter: Int = -1,
    val tier: Int = -1,
    val isObtained: Int = -1,
    val blankSpace: Int = 1
)

fun getRelic(relic: AbstractRelic?) = relic?.let {
    Relic(
        hashed(it.relicId),
        it.price,
        it.counter,
        it.tier?.ordinal ?: -1,
        toInt(it.isObtained),
        0
    )
} ?: Relic()

fun boundedRelicArray(content: ArrayList<AbstractRelic?>?, maxSize: Int = 30) = bounded(maxSize, Relic(), content) { getRelic(it) }
