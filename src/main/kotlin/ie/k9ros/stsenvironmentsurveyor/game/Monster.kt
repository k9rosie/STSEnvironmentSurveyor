package ie.k9ros.stsenvironmentsurveyor.game

import com.megacrit.cardcrawl.monsters.AbstractMonster
import ie.k9ros.stsenvironmentsurveyor.utils.bounded
import ie.k9ros.stsenvironmentsurveyor.utils.toInt
import kotlinx.serialization.Serializable

@Serializable
data class Monster(
    val type: Int = -1,
    val intent: Int = -1,
    val intentDamage: Int = -1,
    val currentHealth: Int = -1,
    val currentBlock: Int = -1,
    val maxHealth: Int = -1,
    val lastDamageTaken: Int = -1,
    val isEscaped: Int = -1,
    val isEscaping: Int = -1,
    val isAboutToEscape: Int = -1,
    val isDying: Int = -1,
    val isDead: Int = -1,
    val powers: List<Power> = bounded(10, Power()),
)

fun getMonster(monster: AbstractMonster?) = monster?.let {
    Monster(
        it.type?.ordinal ?: -1,
        it.intent?.ordinal ?: -1,
        it.intentDmg,
        it.currentHealth,
        it.currentBlock,
        it.maxHealth,
        it.lastDamageTaken,
        toInt(it.escaped),
        toInt(it.isEscaping),
        toInt(it.escapeNext),
        toInt(it.isDying),
        toInt(it.isDead),
        boundedPowerArray(it.powers)
    )
} ?: Monster()

fun boundedMonsterArray(content: ArrayList<AbstractMonster?>?, maxSize: Int = 10) = bounded(maxSize, Monster(), content) { getMonster(it) }
