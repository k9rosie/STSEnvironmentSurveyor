package ie.k9ros.stsenvironmentsurveyor.game

import com.megacrit.cardcrawl.monsters.AbstractMonster
import ie.k9ros.stsenvironmentsurveyor.utils.bounded
import ie.k9ros.stsenvironmentsurveyor.utils.normalize
import ie.k9ros.stsenvironmentsurveyor.utils.toDouble
import kotlinx.serialization.Serializable

@Serializable
data class Monster(
    val type: Double = -1.0,
    val intent: Double = -1.0,
    val intentDamage: Double = -1.0,
    val currentHealth: Double = -1.0,
    val currentBlock: Double = -1.0,
    val maxHealth: Double = -1.0,
    val lastDamageTaken: Double = -1.0,
    val isEscaped: Double = -1.0,
    val isEscaping: Double = -1.0,
    val isAboutToEscape: Double = -1.0,
    val isDying: Double = -1.0,
    val isDead: Double = -1.0,
    val powers: List<Power> = bounded(10, Power()),
)

fun getMonster(monster: AbstractMonster?) = monster?.let {
    Monster(
        normalize(it.type?.ordinal ?: -1),
        normalize(it.intent?.ordinal ?: -1),
        normalize(it.intentDmg),
        normalize(it.currentHealth),
        normalize(it.currentBlock),
        normalize(it.maxHealth),
        normalize(it.lastDamageTaken),
        toDouble(it.escaped),
        toDouble(it.isEscaping),
        toDouble(it.escapeNext),
        toDouble(it.isDying),
        toDouble(it.isDead),
        boundedPowerArray(it.powers, 10)
    )
} ?: Monster()

fun boundedMonsterArray(content: ArrayList<AbstractMonster?>?, maxSize: Int) = bounded(maxSize, Monster(), content) { getMonster(it) }
