package ie.k9ros.stsenvironmentsurveyor.game

import com.megacrit.cardcrawl.powers.AbstractPower
import ie.k9ros.stsenvironmentsurveyor.utils.bounded
import ie.k9ros.stsenvironmentsurveyor.utils.hashed
import ie.k9ros.stsenvironmentsurveyor.utils.normalize
import kotlinx.serialization.Serializable

@Serializable
data class Power(
    val id: Double = -1.0,
    val amount: Double = -1.0,
    val type: Double = -1.0,
)

fun getPower(power: AbstractPower?) = power?.let {
    Power(
        hashed(it.ID),
        normalize(it.amount),
        normalize(it.type?.ordinal ?: -1),
    )
} ?: Power()

fun boundedPowerArray(content: ArrayList<AbstractPower?>?, maxSize: Int) = bounded(maxSize, Power(), content) { getPower(it) }
