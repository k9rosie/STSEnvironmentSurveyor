package ie.k9ros.stsenvironmentsurveyor.game

import com.megacrit.cardcrawl.powers.AbstractPower
import ie.k9ros.stsenvironmentsurveyor.utils.bounded
import ie.k9ros.stsenvironmentsurveyor.utils.hashed
import kotlinx.serialization.Serializable

@Serializable
data class Power(
    val id: Int = -1,
    val amount: Int = -1,
    val type: Int = -1,
)

fun getPower(power: AbstractPower?) = power?.let {
    Power(
        hashed(it.ID),
        it.amount,
        it.type?.ordinal ?: -1
    )
} ?: Power()

fun boundedPowerArray(content: ArrayList<AbstractPower?>?, maxSize: Int = 10) = bounded(maxSize, Power(), content) { getPower(it) }
